package com.seungmoo.java8to11;

import java.util.function.Function;

/**
 * class 로 Function을 만들어보자.
 */
// T : 입력 값 타입, R : 리턴 값 타입
public class Plus10 implements Function<Integer, Integer> {
    @Override
    public Integer apply(Integer integer) {
        return integer + 10;
    }
}
