package com.process.auth.core.config.database.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Danfeng
 * @since 2018/12/9
 */
@MapperScan({
        "com.process.auth.*.*.mapper",
        "com.process.auth.*.*.*.mapper",
})
@Configuration
public class PsMapperConfig {

}
