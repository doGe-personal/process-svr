package com.process.auth.core.config.global.domain;

import org.springframework.http.ResponseEntity;

/**
 * @author Danfeng
 * @since 2018/12/10
 */
public interface PsFeignExceptionResolver {

    /**
     * 判断类型
     *
     * @param status 状态码
     * @return
     */
    boolean support(int status);

    /**
     * 处理异常
     *
     * @param httpStatus 返回状态码
     * @return 返回结果
     */
    ResponseEntity handle(int httpStatus);

}
