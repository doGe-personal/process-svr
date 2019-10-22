package com.process.learn.guava.base;

import com.google.common.collect.Ordering;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.Test;

/**
 * @author Lynn
 * @since 2019/10/18
 */
public class OrderTest {

    @Test
    public void testCompare() {
        int compare = Ordering.natural().compare("", "aa");
        int compare1 = Ordering.natural().nullsFirst().compare(null, "aa");
        System.out.println(compare);
        ArrayList<Integer> integers = Lists.newArrayList(1, 2, 3);
        List<Integer> integers1 = Ordering.natural().greatestOf(integers, 1);
        System.out.println(integers1);
        boolean ordered = Ordering.natural().isOrdered(integers);
        System.out.println(ordered);
        Integer min = Ordering.natural().min(1, 2);
        System.out.println(min);
        Integer max = Ordering.natural().max(1, 2);
        System.out.println(max);

    }
}
