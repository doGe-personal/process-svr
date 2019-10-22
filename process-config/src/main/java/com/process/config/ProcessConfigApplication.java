package com.process.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author Danfeng
 * @since 2018/11/12
 */
@Slf4j
@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication(
        exclude = {
                DataSourceAutoConfiguration.class
        }
)
public class ProcessConfigApplication {
    public static void main(String[] args) {
        log.info("PROCESS-CONFIG-SERVER START RUN...");
        SpringApplication.run(ProcessConfigApplication.class, args);
        log.info("PROCESS-CONFIG-SERVER STARTED");
    }
}
