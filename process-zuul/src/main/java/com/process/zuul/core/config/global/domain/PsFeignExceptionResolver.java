package com.process.zuul.core.config.global.domain;

import feign.FeignException;
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
     * @param exception 返回异常信息
     * @return 返回结果
     */
    ResponseEntity handle(FeignException exception);

}
