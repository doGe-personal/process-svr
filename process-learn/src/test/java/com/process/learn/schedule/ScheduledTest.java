package com.process.learn.schedule;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Lynn
 * @since 2019-05-29
 */
public class ScheduledTest {

    private static ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(100);
    private ThreadLocal local = new ThreadLocal();

    public static void main(String[] args) {
        ScheduledFuture<?> scheduledFuture = scheduledThreadPoolExecutor
                .scheduleWithFixedDelay(new ScheduledTask(), 0, 5, TimeUnit.SECONDS);

    }

}
