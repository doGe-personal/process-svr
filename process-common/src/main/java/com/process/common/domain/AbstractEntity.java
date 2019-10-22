package com.process.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Persistable;

/**
 * @author Danfeng
 * @since 2018/12/9
 */
@Data
@EqualsAndHashCode(of = "id")
public abstract class AbstractEntity implements Persistable<Long> {

    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return id == null || id <= 0L;
    }

}
