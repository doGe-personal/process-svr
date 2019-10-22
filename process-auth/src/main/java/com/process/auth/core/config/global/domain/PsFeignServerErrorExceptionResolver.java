package com.process.auth.core.config.global.domain;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author Danfeng
 * @since 2018/12/10
 */
@Component
public class PsFeignServerErrorExceptionResolver implements PsFeignExceptionResolver {

    private static final String PS_RESULT_CODE = "ps_result_code";
    private static final String NG_SERVICE_ERROR = "NG_SERVICE";

    @Override
    public boolean support(int status) {
        return status >= HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    @Override
    public ResponseEntity handle(int httpStatus) {
        return ResponseEntity.status(httpStatus).header(PS_RESULT_CODE, NG_SERVICE_ERROR).build();
    }

}
