package com.seungmoo.java8to11.interface_study;

public interface Foo {
    void printName();

    // 기본 메소드 (default Method)
    // 이미 이 인터페이스를 구현체 클래스를 깨트리지 않고 새 기능을 추가할 수 있다.
    // •	기본 메소드는 구현체가 모르게 추가된 기능으로 그만큼 리스크가 있다.
    //      o	컴파일 에러는 아니지만 구현체에 따라 런타임 에러가 발생할 수 있다.
    //      o	반드시 문서화 할 것. (@implSpec 자바독 태그 사용)

    /**
     * @implSpec 이 구현체는 getName()으로 가져온 문자열을 대문자로 바꿔서 출력한다.
     */
    default void printNameUpperCase() {
        System.out.println(getName().toUpperCase());
    }

    /**
     * default 메서드는 Object의 메서드를 Override 할 수 없다.
     * @return
     */
    //default String toString() {
    //}

    String getName();

    /**
     * static method
     * •	해당 타입 관련 헬터 또는 유틸리티 메소드를 제공할 때 인터페이스에 스태틱 메소드를 제공할 수 있다.
     */
    static void printAnything() {
        System.out.println("Foo");
    }
}
