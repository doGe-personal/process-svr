package com.process.zuul.dev.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Danfeng
 * @since 2018/11/22
 */
@RequiredArgsConstructor
@RestController
public class TestApi {

    @GetMapping("/api/dev/csrf")
    public String testCsrfCheck() {
        return "OK";
    }

}
