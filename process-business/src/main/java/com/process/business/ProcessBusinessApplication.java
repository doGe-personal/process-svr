package com.process.business;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 微服务管理
 *
 * @author Danfeng
 * @since 2018/12/12
 */
@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ProcessBusinessApplication {
    public static void main(String[] args) {
        log.info("[PROCESS-BUSINESS] STARTING...");
        SpringApplication.run(ProcessBusinessApplication.class, args);
        log.info("[PROCESS-BUSINESS] STARTED...");
    }
}
