package com.process.zuul;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Danfeng
 * @since 2018/11/21
 */
@SpringBootApplication(
        exclude = {
                DataSourceAutoConfiguration.class
        }
)
@EnableDiscoveryClient
@EnableZuulProxy
@EnableFeignClients
@Slf4j
@EnableOAuth2Sso
public class ProcessZuulApplication {
    public static void main(String[] args) {
        log.info("PROCESS ZUUL STARTING");
        SpringApplication.run(ProcessZuulApplication.class, args);
        log.info("PROCESS ZUUL STARTED");
    }
}
