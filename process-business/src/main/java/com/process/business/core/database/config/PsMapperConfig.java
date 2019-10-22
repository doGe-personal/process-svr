package com.process.business.core.database.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Danfeng
 * @since 2018/12/9
 */
@MapperScan({
        "com.process.business.*.mapper",
        "com.process.business.*.*.mapper",
})
@Configuration
public class PsMapperConfig {

}
