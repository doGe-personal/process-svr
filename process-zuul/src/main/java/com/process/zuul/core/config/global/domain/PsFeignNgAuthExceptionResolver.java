package com.process.zuul.core.config.global.domain;

import com.alibaba.fastjson.JSONObject;
import com.process.common.util.FeignUtil;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author Danfeng
 * @since 2018/12/10
 */
@Component
public class PsFeignNgAuthExceptionResolver implements PsFeignExceptionResolver {
    private static final String PS_RESULT_CODE = "PS_RESULT_CODE";
    private static final String NG_AUTH_CODE = "NG_AUTH";
    private static final String ERROR_CODE = "error";

    @Override
    public boolean support(int httpStatus) {
        return httpStatus == HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public ResponseEntity handle(FeignException exception) {
        JSONObject jsonObject = FeignUtil.parseResponseContent(exception.getMessage());
        return ResponseEntity.status(exception.status()).header(PS_RESULT_CODE, jsonObject.containsKey(ERROR_CODE) ? jsonObject.get(ERROR_CODE).toString() : NG_AUTH_CODE).build();
    }

}
