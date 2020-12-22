package com.seungmoo.java8to11;

/**
 * 추상 메서드가 딱! 하나만 있으면 "함수형 인터페이스"이다.
 */
// 함수형 인터페이스 정의 시, Java에서 제공하는 Annotation을 셋팅해주면 좋다. --> compile시 valid 체크 해줌
@FunctionalInterface
public interface RunSomething {
    // 추상 메서드에서 abstract 키워드는 생략 가능하다.
    abstract void doIt();

    // 추상 메서드가 두 개 있으면 함수형 인터페이스가 아니다.
    // void doIt2();

    /**
     * 아래 메서드들이 있어도 함수형 인터페이스에 영향이 없다.
     * 함수형 인터페이스에서 중요한 것은 : 추상 메서드
     */
    // interface안에 static 메서드를 정의할 수 있다.
    static void printName() {
        System.out.println("Seungmoo");
    }

    // default 메서드를 정의할 수 있다.
    default void printAge() {
        System.out.println("29?");
    }

}
