package com.process.mobike.redis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * @author Lynn
 * @since 2019-07-30
 */
@Slf4j
public class RedisTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.set("key1", "hello world");
        log.info("value: {}", jedis.get("key1"));
    }

}
