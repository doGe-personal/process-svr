package com.process.zuul.core.config.database.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Danfeng
 * @since 2018/12/9
 */
@MapperScan({
        "com.process.zuul.*.mapper",
        "com.process.zuul.*.*.mapper",
})
@Configuration
public class PsMapperConfig {

}
