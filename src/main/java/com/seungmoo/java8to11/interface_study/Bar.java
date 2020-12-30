package com.seungmoo.java8to11.interface_study;

public interface Bar extends Foo {

    // Foo의 printNameUpperCase 메서드를 그대로 쓰고 싶지 않은 경우
    // 추상 메서드로 재정의 할 수 있다.
    //void printNameUpperCase();

    /**
     * Bar에도 Foo랑 동일한 이름의 default 메서드가 있다면???
     */
    default void printNameUpperCase() {
        System.out.println("BAR");
    }

}
