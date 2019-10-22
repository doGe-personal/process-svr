package com.process.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author Danfeng
 * @since 2018/12/17
 */
@SpringBootApplication(
        exclude = {DataSourceAutoConfiguration.class}
)
public class ProcessCommonApplication {
    public static void main(String[] args) {
        System.out.println("[PROCESS-COMMON] starting");
        SpringApplication.run(ProcessCommonApplication.class, args);
        System.out.println("[PROCESS-COMMON] started");
    }
}
