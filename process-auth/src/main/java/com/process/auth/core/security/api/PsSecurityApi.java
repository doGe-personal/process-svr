package com.process.auth.core.security.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Danfeng
 * @since 2018/12/14
 */
@Slf4j
@RestController
@RequestMapping("/api/sec")
public class PsSecurityApi {

    @GetMapping("/principal")
    public Authentication principal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("principal-me:{}", authentication.getName());
        return authentication;
    }

}
