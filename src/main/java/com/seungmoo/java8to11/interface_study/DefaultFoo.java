package com.seungmoo.java8to11.interface_study;

/**
 * Foo, Bar 인터페이스에 똑같은 이름의 default method가 존재하면 --> COMPILE ERROR 가 발생한다.
 * 충돌하는 Default Method의 경우 직접 Override 해줘야 한다.
 */
public class DefaultFoo implements Foo, Bar {
    private String name;

    public DefaultFoo(String name) {
        this.name = name;
    }

    /**
     * Override해서 재정의 가능하다.
     * Foo, Bar 모두 default method printNameUpperCase 있을 경우 COMPILE ERROR 발생!!
     * 아래처럼 직접 Override 해줘야 한다.
     */
    @Override
    public void printNameUpperCase() {
        System.out.println(this.name.toUpperCase());
    }

    @Override
    public void printName() {
        System.out.println(this.name);
    }

    @Override
    public String getName() {
        return this.name;
    }
}
