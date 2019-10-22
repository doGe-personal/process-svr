package com.process.mobike.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Lynn
 * @since 2019-04-23
 */
@Component
public class CustomListener {

    @EventListener
    public void handleOrderEvent(CustomEvent event) {
        System.out.println("我监听到了handleOrderEvent发布的message为:" + event.getMsg());
    }

}
