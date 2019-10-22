package com.process.auth.core.security.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
@Service
public class PsRedisClientDetailsService extends JdbcClientDetailsService {

    private final StringRedisTemplate stringRedisTemplate;
    private static final String CACHE_CLIENT_KEY = "PS_CLIENT_DETAILS";

    @Autowired
    public PsRedisClientDetailsService(DataSource dataSource, StringRedisTemplate stringRedisTemplate) {
        super(dataSource);
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        ClientDetails clientDetails;
        // 先从redis获取
        String value = (String) stringRedisTemplate.boundHashOps(CACHE_CLIENT_KEY).get(clientId);
        if (StringUtils.isBlank(value)) {
            clientDetails = cacheAndGetClient(clientId);
        } else {
            clientDetails = JSONObject.parseObject(value, BaseClientDetails.class);
        }
        return clientDetails;
    }

    @Override
    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
        super.updateClientDetails(clientDetails);
        cacheAndGetClient(clientDetails.getClientId());
    }

    @Override
    public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
        super.updateClientSecret(clientId, secret);
        cacheAndGetClient(clientId);
    }

    @Override
    public void removeClientDetails(String clientId) throws NoSuchClientException {
        super.removeClientDetails(clientId);
        removeRedisCache(clientId);
    }

    private ClientDetails cacheAndGetClient(String clientId) {
        // 从数据库读取
        ClientDetails clientDetails = super.loadClientByClientId(clientId);
        if (clientDetails != null) {
            stringRedisTemplate.boundHashOps(CACHE_CLIENT_KEY).put(clientId, JSONObject.toJSONString(clientDetails));
            log.info("缓存clientId:{},{}", clientId, clientDetails);
        }
        return clientDetails;
    }

    private void removeRedisCache(String clientId) {
        stringRedisTemplate.boundHashOps(CACHE_CLIENT_KEY).delete(clientId);
    }

    public void loadAllClientToCache() {
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(CACHE_CLIENT_KEY))) {
            return;
        }
        log.info("将oauth_client_details全表刷入redis");
        List<ClientDetails> list = super.listClientDetails();
        if (CollectionUtils.isEmpty(list)) {
            log.error("oauth_client_details表数据为空，请检查");
            return;
        }
        list.parallelStream().forEach(client -> stringRedisTemplate.boundHashOps(CACHE_CLIENT_KEY).put(client.getClientId(), JSONObject.toJSONString(client)));
    }

}
