package com.process.business.core.security.config;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author Danfeng
 * @since 2018/12/14
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class PsSecurityConfig {

}
