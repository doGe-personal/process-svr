package com.process.common.domain;

import lombok.Data;

/**
 * @author Danfeng
 * @since 2018/12/31
 */
@Data
public class BaseCodeName implements CodeName {

    /**
     * key
     */
    private Long id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;
}
