package com.process.common.database.domain;

import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @author Danfeng
 * @since 2018/12/9
 */
public class PagingQuery implements Paging, Sortable {
    private static final int DEF_LIMIT = 20;
    private int offset = 0;
    private int limit = 20;
    private String sortBy;
    private Sort.Direction sortDirection;

    @Override
    public Optional<SortOption> getSortOption() {
        return StringUtils.isEmpty(this.sortBy) ? Optional.empty() : Optional.of(SortOption.of(this.sortBy, this.sortDirection));
    }

    public PagingQuery() {
        this.sortDirection = Sort.Direction.ASC;
    }

    @Override
    public int getOffset() {
        return this.offset;
    }

    @Override
    public int getLimit() {
        return this.limit;
    }

    public String getSortBy() {
        return this.sortBy;
    }

    public Sort.Direction getSortDirection() {
        return this.sortDirection;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public void setSortDirection(Sort.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }

}
