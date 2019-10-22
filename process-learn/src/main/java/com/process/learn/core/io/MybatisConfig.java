package com.process.learn.core.io;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Lynn
 * @since 2019-04-18
 */
@Configuration
@MapperScan(
        value = {
                "com.process.mobike.*.mapper",
        }
)
public class MybatisConfig {

}
