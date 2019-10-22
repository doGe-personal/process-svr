package com.process.common.database.domain;

import com.process.common.util.SqlUtil;
import org.apache.ibatis.jdbc.AbstractSQL;

/**
 * @author Danfeng
 * @since 2018/12/9
 */
public abstract class PsSql extends AbstractSQL<PsSql> {
    @Override
    public PsSql getSelf() {
        return this;
    }

    protected String toPagingSql(PagingQuery query) {
        return SqlUtil.paging(this.toString(), query);
    }

    protected String toCountSql() {
        this.SELECT("count(1)");
        return this.toString();
    }

    protected void ORDER_BY(Sortable query) {
        query.getSortOption().ifPresent((option) -> this.ORDER_BY(SqlUtil.toOrderByClause(option)));
    }

}
