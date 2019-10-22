package com.process.common.database.domain;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Danfeng
 * @since 2018/12/11
 */
public class PagingResult<TRow> extends ListingResult<TRow> {
    private final long total;
    private static PagingResult EMPTY = of(Collections.emptyList(), 0L);

    protected PagingResult(Collection<TRow> rows, long total) {
        super(rows);
        this.total = total;
    }

    @SuppressWarnings("unchecked")
    public static <TRow> PagingResult<TRow> of(Collection<TRow> rows, long total) {
        return new PagingResult(rows, total);
    }

    @SuppressWarnings("unchecked")
    public static <TRow> PagingResult<TRow> empty() {
        return EMPTY;
    }

    public long getTotal() {
        return this.total;
    }
}
