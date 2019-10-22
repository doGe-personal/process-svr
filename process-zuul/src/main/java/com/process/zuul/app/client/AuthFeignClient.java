package com.process.zuul.app.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author Danfeng
 * @since 2018/11/26
 */
@FeignClient("process-auth")
public interface AuthFeignClient {

    /**
     * 获取access_token
     *
     * @return token
     */
    @PostMapping(path = "/auth/oauth/token")
    Map<String, Object> accessToken(@RequestParam Map<String, String> parameters);

}
