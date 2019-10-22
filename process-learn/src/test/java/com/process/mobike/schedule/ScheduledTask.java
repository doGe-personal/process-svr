package com.process.mobike.schedule;

import java.util.function.BiConsumer;

/**
 * @author Lynn
 * @since 2019-05-29
 */
public class ScheduledTask implements Runnable {

    private Integer times = 1;
    private BiConsumer biConsumer;

    @Override
    public void run() {
        times++;
        System.out.println("Task Run" + times);
        // 调用WMS ==》 true pool remove
        // times == 3 // false remove

    }

}
