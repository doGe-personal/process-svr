package com.process.common.database.domain;

import java.util.Map;

/**
 * @author Danfeng
 * @since 2018/12/9
 */
public interface SqlDialect {
    Map<Character, String> escapeTable();

    String escapeClause();

    String paging(String var1, Paging var2);
}
