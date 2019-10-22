package com.process.mobike.listener;

import com.process.mobike.MbkBaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * @author Lynn
 * @since 2019-04-23
 */

public class CustomListenerTest extends MbkBaseTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void listener() {
       context.publishEvent(CustomEvent.builder().msg("测试方法").build());
    }

}
