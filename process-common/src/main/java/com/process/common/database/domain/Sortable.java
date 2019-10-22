package com.process.common.database.domain;

import java.util.Optional;

/**
 * @author Danfeng
 * @since 2018/12/9
 */
public interface Sortable {
    Optional<SortOption> getSortOption();
}
