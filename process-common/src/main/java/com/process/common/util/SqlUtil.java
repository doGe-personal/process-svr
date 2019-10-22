package com.process.common.util;


import com.process.common.database.domain.Paging;
import com.process.common.database.domain.SortOption;
import com.process.common.database.domain.SqlDialect;
import org.apache.ibatis.jdbc.AbstractSQL;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Danfeng
 * @since 2018/12/9
 */
public abstract class SqlUtil {
    public static SqlDialect SQL_DIALECT;
    static final String SQL_EMPTY_SET = "(NULL)";
    private static final Pattern RE_WORD = Pattern.compile("^\\w+$");

    public static String paging(String sql, Paging paging) {
        if (paging != null) {
            sql = SQL_DIALECT.paging(sql, paging);
        }

        return sql;
    }

    public static String toOrderByClause(SortOption... options) {
        if (options.length == 0) {
            throw DevError.unexpected("Parameter(options) is empty");
        } else {
            return Stream.of(options).map(SortOption::toSqlSort).collect(Collectors.joining(","));
        }
    }

    public static String toSqlString(CharSequence val) {
        if (StringUtils.isEmpty(val)) {
            throw DevError.unexpected("Parameter(val) is null or empty");
        } else {
            return escapeString(new StringBuilder(), val).toString();
        }
    }

    private static StringBuilder escapeString(StringBuilder sb, CharSequence input) {
        sb.append('\'');
        int i = 0;

        for (int n = input.length(); i < n; ++i) {
            char ch = input.charAt(i);
            sb = ch == '\'' ? sb.append("''") : sb.append(ch);
        }

        sb.append('\'');
        return sb;
    }

    public static String toSqlLikeString(CharSequence val, SqlUtil.SqlLikeOption option) {
        if (StringUtils.isEmpty(val)) {
            throw DevError.unexpected("Parameter(val) is null or empty");
        } else {
            return escapeLikeString(new StringBuilder(), val, option).toString();
        }
    }

    private static StringBuilder escapeLikeString(StringBuilder sb, CharSequence input, SqlUtil.SqlLikeOption option) {
        Map<Character, String> escapeTable = SQL_DIALECT.escapeTable();
        sb.append(option != SqlUtil.SqlLikeOption.RIGHT_SIDE ? "'%" : "'");
        int i = 0;

        for (int n = input.length(); i < n; ++i) {
            char ch = input.charAt(i);
            String escaped = escapeTable.get(ch);
            sb = escaped != null ? sb.append(escaped) : sb.append(ch);
        }

        sb.append(option != SqlUtil.SqlLikeOption.LEFT_SIDE ? "%'" : "'");
        sb.append(SQL_DIALECT.escapeClause());
        return sb;
    }

    public static String toSqlLikeString(String val) {
        return toSqlLikeString(val, SqlUtil.SqlLikeOption.BOTH_SIDES);
    }

    public static String toSqlStringSet(Collection<? extends CharSequence> vals) {
        List<? extends CharSequence> nonNullVals = vals.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return nonNullVals.isEmpty() ? "(NULL)" : nonNullVals.stream().map(SqlUtil::toSqlString).collect(Collectors.joining(",", "(", ")"));
    }

    public static String toSqlNumberSet(Collection<? extends Number> vals) {
        List<? extends Number> nonNullVals = vals.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return nonNullVals.isEmpty() ? "(NULL)" : nonNullVals.stream().map(String::valueOf).collect(Collectors.joining(",", "(", ")"));
    }

    public static String unionAll(SQL... sqls) {
        return Stream.of(sqls).map(AbstractSQL::toString).collect(Collectors.joining(" UNION ALL "));
    }

    public static String toSqlWord(String input) {
        if (!RE_WORD.matcher(input).matches()) {
            throw new IllegalArgumentException("[CORE-JDBC] Illegal inputted sql word: " + input);
        } else {
            return input;
        }
    }

    public enum SqlLikeOption {
        /**
         * SQL LIKE
         */
        BOTH_SIDES,
        LEFT_SIDE,
        RIGHT_SIDE
    }
}
