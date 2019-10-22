package com.process.mobike.listener;

import lombok.Builder;
import lombok.Data;

/**
 * @author Lynn
 * @since 2019-04-23
 */
@Data
@Builder
public class CustomEvent {
    private String msg;
}
