package com.process.common.database.dialect;

import com.process.common.database.domain.SqlDialect;
import com.process.common.database.domain.Paging;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Danfeng
 * @since 2018/12/9
 */
public class SqlDialectMySQL implements SqlDialect {
    private static final Map<Character, String> SQL_ESC = new HashMap<>();

    @Override
    public Map<Character, String> escapeTable() {
        return SQL_ESC;
    }

    @Override
    public String escapeClause() {
        return "";
    }

    @Override
    public String paging(String querySql, Paging paging) {
        return querySql + " LIMIT " + paging.getOffset() + "," + paging.getLimit();
    }

    static {
        SQL_ESC.put('\'', "''");
        SQL_ESC.put('%', "\\%");
        SQL_ESC.put('_', "\\_");
    }
}
