package com.seungmoo.java8to11;

/**
 * 파라미터를 받고 리턴하는 함수
 */
@FunctionalInterface
public interface RunSomethingWithParam {
    int doIt(int number);
}
