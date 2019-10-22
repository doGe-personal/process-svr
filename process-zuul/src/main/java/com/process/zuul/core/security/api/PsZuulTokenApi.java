package com.process.zuul.core.security.api;

import com.process.common.util.SecurityUtil;
import com.process.zuul.app.client.AuthFeignClient;
import com.process.zuul.core.security.entity.PsClientCredentials;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Danfeng
 * @since 2018/11/22
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sec")
public class PsZuulTokenApi {

    private final AuthFeignClient authClient;
    private final ClientCredentialsResourceDetails resourceDetails;


    @GetMapping("/test")
    public ResponseEntity test() {
        return ResponseEntity.ok("Test");
    }

    /**
     * csrf
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/csrf")
    public ResponseEntity csrf(HttpServletRequest request, HttpServletResponse response) {
        SecurityUtil.createXsrfToken(request, response);
        return ResponseEntity.ok().build();
    }

    /**
     * 系统登陆
     * 根据用户名登录
     * 采用oauth2密码模式获取access_token和refresh_token
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody PsClientCredentials credentials) {
        credentials.swapClient(resourceDetails);
        return authClient.accessToken(credentials.tokenParams());
    }

}
