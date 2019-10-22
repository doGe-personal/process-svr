package com.process.mobike.refactor.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Lynn
 * @since 2019-05-23
 */
@Data
@AllArgsConstructor
public class Movie {

    public static final int CHILDRENS = 2;
    public static final int REGAULAR = 0;
    public static final int NEW_RELEASE = 1;

    private String title;
    private int priceCode;


}
