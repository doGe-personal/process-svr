package com.process.business.app.mapper;

import com.process.business.app.entity.OrderEntity;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * @author Lynn
 * @since 2019-09-03
 */
public interface OrderMapper {

    @Insert({
            "INSERT INTO ps_order",
            "(order_no, order_user_id, order_time)",
            "VALUES(#{orderNo}, #{orderUserId}, #{orderTime})"
    })
    int insertOrder(OrderEntity entity);

    @Select({
            "SELECT * FROM PS_ORDER"
    })
    List<OrderEntity> getOrders();

}
