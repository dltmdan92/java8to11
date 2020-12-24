package com.seungmoo.java8to11;

import java.util.function.*;

//@SpringBootApplication
public class LambdaApplication {

    private static void lambda1() {
        //SpringApplication.run(Java8to11Application.class, args);

        // 이런 것을 익명 내부 클래스라고 한다. --> 함수형 인터페이스의 경우에는 람다형태를 적용할 수 있다.
        // 람다형태를 통해 코드를 많이 줄일 수 있다.
        RunSomething runSomething = new RunSomething() {
            @Override
            public void doIt() {
                System.out.println("Hello");
            }
        };

        // 람다식 적용
        RunSomething runSomething1 = () -> System.out.println("Hello");
        runSomething1.doIt();

        // 두 줄 이상부터는 {}으로 감싼다.
        RunSomething runSomething2 = () -> {
            System.out.println("Hello");
            System.out.println("Hello2");
        };
        runSomething2.doIt();

        RunSomethingWithParam runSomethingWithParam = (number) -> {
            return number + 10;
        };

        // 함수형 프로그래밍은 같은 parameter를 넣으면 같은 result가 나와야 한다.
        // Pure한 함수 : 오로지 parameter로 전달한 값만 사용하고 외부 변수는 쓰지 않기
        runSomethingWithParam.doIt(20);
        runSomethingWithParam.doIt(20);
        runSomethingWithParam.doIt(20);

        // Pure하지 못한 케이스 1
        // 상태 값을 갖는 함수
        RunSomethingWithParam runSomethingWithParam1 = new RunSomethingWithParam() {
            int baseNumber = 10; // 함수가 내부에 상태 값을 갖는다.
            @Override
            public int doIt(int number) {
                return number + baseNumber;
            }
        };

        // Pure X 한 케이스 2
        // 외부 변수를 갖다 쓰는 케이스
        // final한 변수만 쓸 수 있다. final 안붙여도 java compiler가 final로 셋팅함. (아래 줄에서 값 변경 불가)
        final int baseNumber = 10;

        RunSomethingWithParam runSomethingWithParam2 = new RunSomethingWithParam() {
            @Override
            public int doIt(int number) {
                return number + baseNumber;
            }
        };

        // class로 만들어진 Function
        Plus10 plus10 = new Plus10();
        System.out.println(plus10.apply(1));

        // Function interface를 사용해서 함수를 곧바로 만들 수 있다.
        Function<Integer, Integer> plus11 = i -> i + 11;
        Function<Integer, Integer> multiply2 = i -> i * 2;
        System.out.println(multiply2.apply(2));

        // 고차 함수
        // multiply2의 결과값을 plus11 함수의 입력 값으로 사용한다.
        Function<Integer, Integer> multiply2AndPlus10 = plus11.compose(multiply2);
        System.out.println(multiply2AndPlus10.apply(2)); // (2 * 2) + 11 = 15

        // plus11 뒤에 multiply2 함수를 붙이는 것.
        // plus11의 결과값을 multiply2의 입력값으로 넣어서 실행한다.
        System.out.println(plus11.andThen(multiply2).apply(2)); // (2 + 11) * 2 = 26

        // Comsumer는 void만 있기 때문에 return은 못한다.
        Consumer<Integer> printT = i -> System.out.println(i);
        printT.accept(10);

        // T 타입의 값을 제공한다.
        Supplier<Integer> get10 = () -> 10;
        System.out.println(get10.get());

        // 인자를 받아서 True, False를 return
        Predicate<String> startsWith = s -> s.startsWith("seungmoo"); // 인자를 받아서 seungmoo로 시작하는 지 확인
        startsWith.test("seungmoolee");
        Predicate<Integer> isEven = i -> i % 2 == 0;

        // 입력값과 리턴값의 Type이 같은경우 (쪼끔 더 깔끔)
        UnaryOperator<Integer> plus10Unary = i -> i + 10;

        // BinaryOperator<T> = BiFunction<T, T, T> ==> 3개 타입이 모두 같을 때 (앞에 두개는 파라미터, 뒤에 한 개는 리턴)
    }

    private static void lambda2() {
        // parameter 없으면 그냥 (), 10 리턴하는 바디가 한 줄 일때는 그냥 10 적어준다.
        Supplier<Integer> get10 = () -> 10;

        // 인자가 두개 인 경우, BiFunction
        BiFunction<Integer, Integer, Integer> plus = (a, b) -> a + b;
        // 인자 두 개와, 리턴 타입이 모두 같은 타입
        BinaryOperator<Integer> plus2 = (a, b) -> a + b;

        // java 8 부터 이 변수가 사실상 final 일 경우, final 생략이 가능하다. (람다)
        int baseNumber = 10; // final 생략했음

        // 로컬 클래스, 외부 변수와 내부 변수 name이 같을 경우 shadowing 된다. (스코프가 다름)
        class LocalClass {
            int baseNumber = 5;
            void printBaseNumber() {
                System.out.println(baseNumber);
            }
        }

        // 익명 클래스, 외부 변수와 내부 변수 name이 같을 경우 shadowing 된다. (스코프가 다름)
        Consumer<Integer> integerConsumer = new Consumer<Integer>() {
            int baseNumber = 5;
            @Override
            public void accept(Integer baseNumber) {
                System.out.println(baseNumber);
            }
        };

        // 람다 외부 로컬 변수 참조할 때는, 로컬 클래스, 익명 클래스와는 다르다.
        // 공통점은 외부 변수(baseNumber)를 참조할 수 있다.
        // 차이점 : 람다에서는 변수 쉐도잉이 안된다. (외부 변수와 내부 변수의 name이 같은 경우 외부 변수는 쉐도잉 처리가 안된다.)
        // "같은 스코프" 이기 때문이다.
        IntConsumer printInt = i -> {
            //int baseNumber = 5;
            System.out.println(i + baseNumber);
        };

        // 이렇게 변수가 수정이 발생하는 경우에는 람다식 컴파일할 때 에러남.
        //baseNumber++;

        printInt.accept(10);
    }

    public static void main(String[] args) {
        lambda2();
    }

}
