package com.process.business.app.entity;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author Lynn
 * @since 2019-09-03
 */
@Data
public class OrderEntity {
    private Long id;
    private String tenantId;
    private String orderNo;
    private Long orderUserId;
    private LocalDateTime orderTime;
}
