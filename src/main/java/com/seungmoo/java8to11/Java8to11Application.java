package com.seungmoo.java8to11;

//@SpringBootApplication
public class Java8to11Application {

    public static void main(String[] args) {
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
    }

}
