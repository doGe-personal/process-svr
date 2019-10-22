package com.process.learn.guava.base;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkElementIndex;
import static com.google.common.base.Preconditions.checkPositionIndex;
import static com.google.common.base.Preconditions.checkState;

import org.junit.Test;

/**
 * @author Lynn
 * @since 2019-09-10
 */
public class PreCheckTest {

    @Test
    public void testPreCheck() {
//        int a = 9;
//        checkArgument(a>=9, "Argument was %s but expected nonnegative", a);
//
//        List<Integer> intList=new ArrayList<Integer>();
//        for(int i=0;i<10;i++){
//            try {
//                checkState(intList.size()<i, " intList size 不能大于"+i);
//                intList.add(i);
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//        }

//        checkPositionIndex(10,9);
//        checkPositionIndexes(10,11, 20);
        checkElementIndex(10,20);
    }

}
