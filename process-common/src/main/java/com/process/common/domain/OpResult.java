package com.process.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Danfeng
 * @since 2018/12/12
 */
public interface OpResult {
    String HTTP_HEADER_ATTR = "PS-RESULT-CODE";
    String OK_CODE = "OK";
    String NG_CODE = "NG";
    OpResult OK = () -> {
        return "OK";
    };
    OpResult NG = () -> {
        return "NG";
    };

    @JsonIgnore
    String name();

    @JsonIgnore
    default String getDesc() {
        return this.name();
    }

    default String getCode() {
        return this.name();
    }

    default String getName() {
        return this.getDesc();
    }

}
