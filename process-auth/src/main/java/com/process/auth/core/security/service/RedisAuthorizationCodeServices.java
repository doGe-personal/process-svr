package com.process.auth.core.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

//@Service
public class RedisAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        redisTemplate.execute((RedisCallback<Long>) connection -> {
            connection.set(codeKey(code).getBytes(), SerializationUtils.serialize(authentication),
                    Expiration.from(10, TimeUnit.MINUTES), SetOption.UPSERT);
            return 1L;
        });
    }

    @Override
    protected OAuth2Authentication remove(final String code) {
        return redisTemplate.execute((RedisCallback<OAuth2Authentication>) connection -> {
            byte[] keyByte = codeKey(code).getBytes();
            byte[] valueByte = connection.get(keyByte);
            if (valueByte != null) {
                connection.del(keyByte);
                return SerializationUtils.deserialize(valueByte);
            }
            return null;
        });
    }

    /**
     * 拼装redis中key的前缀
     *
     * @param code
     */
    private String codeKey(String code) {
        return "oauth2:codes:" + code;
    }
}
