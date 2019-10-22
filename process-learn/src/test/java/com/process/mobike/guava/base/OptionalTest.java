package com.process.mobike.guava.base;

import com.google.common.base.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author Lynn
 * @since 2019-09-10
 */
@Slf4j
public class OptionalTest {

    @Test
    public void commonMethod() {
        Optional<String> stringOptional = Optional.of("String,String");
        Set<String> strings = stringOptional.asSet();
        log.info("strings --> {}", strings);
        stringOptional.or("aaa");

    }

}
