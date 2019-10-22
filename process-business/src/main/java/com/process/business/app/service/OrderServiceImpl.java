package com.process.business.app.service;

import com.process.business.app.entity.OrderEntity;
import com.process.business.app.mapper.OrderMapper;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Lynn
 * @since 2019-09-03
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    @Override
    public void saveOrder(OrderEntity entity) {
        entity.setOrderTime(LocalDateTime.now());
        orderMapper.insertOrder(entity);
    }

    @Override
    public List<OrderEntity> getOrders() {
        return orderMapper.getOrders();
    }

}
