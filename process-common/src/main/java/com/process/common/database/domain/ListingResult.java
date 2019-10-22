package com.process.common.database.domain;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Danfeng
 * @since 2018/12/9
 */
public class ListingResult<TRow> {
    private final Collection<TRow> rows;
    private static ListingResult EMPTY = of(Collections.emptyList());

    protected ListingResult(Collection<TRow> rows) {
        this.rows = rows;
    }

    @SuppressWarnings("unchecked")
    public static <TRow> ListingResult<TRow> of(Collection<TRow> rows) {
        return new ListingResult(rows);
    }

    @SuppressWarnings("unchecked")
    public static <TRow> ListingResult<TRow> empty() {
        return EMPTY;
    }

    @Override
    public String toString() {
        return "ListingResult{rows=" + this.rows + '}';
    }

    public Collection<TRow> getRows() {
        return this.rows;
    }

}
