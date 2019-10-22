package com.process.business.app.service;

import com.process.business.app.entity.OrderEntity;
import java.util.List;

/**
 * @author Lynn
 * @since 2019-09-03
 */
public interface OrderService {

    void saveOrder(OrderEntity entity);

    List<OrderEntity> getOrders();
}
