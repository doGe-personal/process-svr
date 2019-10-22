package com.process.eureka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Danfeng
 * @since 2018/11/12
 */
@Slf4j
@SpringBootApplication(
        exclude = {DataSourceAutoConfiguration.class}
)
@EnableEurekaServer
public class ProcessEurekaApplication {

    public static void main(String[] args) {
        log.info("PROCESS-EUREKA STARTING...");
        SpringApplication.run(ProcessEurekaApplication.class, args);
        log.info("PROCESS-EUREKA STARTED");
    }

}
