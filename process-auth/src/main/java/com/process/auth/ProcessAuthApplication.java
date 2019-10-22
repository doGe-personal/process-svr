package com.process.auth;

import com.process.common.database.dialect.SqlDialectMySQL;
import com.process.common.util.SqlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 权限中心
 *
 * @author Danfeng
 * @since 2018/11/17
 */
@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ProcessAuthApplication {

    static {
        SqlUtil.SQL_DIALECT = new SqlDialectMySQL();
    }

    public static void main(String[] args) {
        log.info("PROCESS-AUTH starting");
        SpringApplication.run(ProcessAuthApplication.class, args);
        log.info("PROCESS-AUTH started");
    }
}
