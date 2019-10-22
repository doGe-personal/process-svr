package com.process.common.database.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.process.common.util.SqlUtil;
import org.springframework.data.domain.Sort;

/**
 * @author Danfeng
 * @since 2018/12/9
 */
public class SortOption {
    @JsonProperty("by")
    private String sortBy;
    @JsonProperty("dir")
    private Sort.Direction sortDirection;

    public String toSqlSort() {
        return SqlUtil.toSqlWord(this.sortBy) + " " + this.sortDirection.name();
    }

    public Sort toSort() {
        return new Sort(this.sortDirection, new String[]{this.sortBy});
    }

    public static SortOption of(String by, Sort.Direction direction) {
        SortOption sortOption = new SortOption();
        sortOption.setSortBy(by);
        sortOption.setSortDirection(direction);
        return sortOption;
    }

    public static SortOption of(String by, String direction) {
        return of(by, Sort.Direction.fromString(direction));
    }

    public SortOption() {
    }

    public String getSortBy() {
        return this.sortBy;
    }

    public Sort.Direction getSortDirection() {
        return this.sortDirection;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public void setSortDirection(Sort.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }

}
