package com.process.business.app.api;

import com.process.business.app.entity.OrderEntity;
import com.process.business.app.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lynn
 * @since 2019-09-03
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderApi {

    private final OrderService orderService;

    @PostMapping("order")
    public ResponseEntity saveOrder(@RequestBody OrderEntity entity) {
        orderService.saveOrder(entity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("orders")
    public ResponseEntity getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

}
