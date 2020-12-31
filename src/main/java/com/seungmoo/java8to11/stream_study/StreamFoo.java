package com.seungmoo.java8to11.stream_study;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamFoo {

    public void runStream() {
        List<String> names = new ArrayList<>();
        names.add("seungmoo");
        names.add("sam");
        names.add("toby");
        names.add("spring");

        // stream으로 전달받은 데이터들은 변경이 없다.
        // stream으로 처리하는 데이터는 오직 한번만 처리
        // 무제한이 될 수 있다. (실시간으로 들어오는 데이터를 계속 스트림으로 처리) --> Short Circuit (제한 메소드)로 제한 가능
        // 중개형 오퍼레이션 : stream을 리턴한다. (•	filter, map, limit, skip, sorted, ...) •
        //      --> Stateless / Stateful 오퍼레이션으로 더 상세하게 구분할 수도 있다.
        //      --> (대부분은 Stateless지만 distinct나 sorted 처럼 이전 이전 소스 데이터를 참조해야 하는 오퍼레이션은 Stateful 오퍼레이션이다.)
        //      --> 중개형 오퍼레이션은 종료형 오퍼레이션이 선언되어야 실행된다. --> Lazy 하게 처리 (단지 정의할 뿐임)
        // 종료 오퍼레이션
        //      --> Stream을 리턴하지 않는다.
        //      --> collect, allMatch, count, forEach, min, max, ...
        // 종료형 오퍼레이터는 반드시 하나 정의 되어야 한다. (안그러면 중개형 오퍼레이터는 실행이 안된다. (Lazy))

        names.stream()
                // 중개형 오퍼레이터
                .map(String::toUpperCase)
                .map(String::toLowerCase)
                // 종료형 오퍼레이터
                .forEach(System.out::println);

        // jvm에 알아서 병렬 처리 해준다.
        // 아래 오퍼레이터(map)을 병렬적으로 처리해준다. (멀티쓰레드, 순서보장X)
        // 데이터 양이 방대하게 많을 경우에는 병렬처리(멀티쓰레딩)가 좋다.
        // 마지막으로 collect로 모아준다.
        List<String> collect = names.parallelStream()
                                .map(s -> {
                                    System.out.println(s + " " + Thread.currentThread().getName());
                                    return s.toUpperCase();
                                })
                                .collect(Collectors.toList());

        collect.forEach(System.out::println);
    }

    /**
     * stream API 를 좀 더 사용해보자
     */
    public void streamAPI2() {
        List<OnlineClass> springClasses = new ArrayList<>();
        springClasses.add(new OnlineClass(1, "spring boot", true));
        springClasses.add(new OnlineClass(2, "spring data jpa", true));
        springClasses.add(new OnlineClass(3, "spring mvc", false));
        springClasses.add(new OnlineClass(4, "spring core", false));
        springClasses.add(new OnlineClass(5, "rest api development", false));

        System.out.println("spring으로 시작하는 수업");
        // TODO
        springClasses.stream()
                // filter operation
                .filter(onlineClass -> onlineClass.getTitle().startsWith("spring"))
                .forEach(onlineClass -> System.out.println(onlineClass.getId()));

        System.out.println("close 되지 않은 수업");
        // TODO
        springClasses.stream()
                //.filter(onlineClass -> !onlineClass.isClosed())
                // 이렇게 사용할 수 있다. (아쉽게도 메서드 레퍼런스에는 not이 없으므로 Predicate를 활용하자.)
                .filter(Predicate.not(OnlineClass::isClosed))
                .forEach(onlineClass -> System.out.println(onlineClass.getId()));

        System.out.println("수업 이름만 모아서 스트림 만들기");
        // TODO
        springClasses.stream()
                // map operator에서 string 타입으로 변경하여 return 한다. --> 그 다음 오퍼레이터는 map이 변경한 타입으로 받는다.
                .map(OnlineClass::getTitle)
                .forEach(System.out::println);

        List<OnlineClass> javaClasses = new ArrayList<>();
        javaClasses.add(new OnlineClass(6, "The Java Test", true));
        javaClasses.add(new OnlineClass(7, "The Java, Clode manupulation", true));
        javaClasses.add(new OnlineClass(8, "The Java, 8 to 11", false));

        List<List<OnlineClass>> keesunEvents = new ArrayList<>();
        keesunEvents.add(springClasses);
        keesunEvents.add(javaClasses);

        /**
         * List<List>> 타입의 Collection을 stream으로 만들면
         * 스트림 내부에는 여러개의 List들이 원소로 존재하게 된다.  [ List, List, List ]
         * 여러개의 List들을 Flatten 시키는 작업 --> FlatMap 이다. 하나의 스트림으로 flatten 시키는 것임.
         * [ List, List, List ] --> [ onlineClass, onlineClass, onlineClass, onlineClass, .... ]
         */

        System.out.println("두 수업 목록에 들어있는 모든 수업 아이디 출력");
        // TODO
        keesunEvents.stream()
                .flatMap(Collection::stream)
                .forEach(onlineClass -> System.out.println(onlineClass.getId()));

        System.out.println("10부터 1씩 증가하는 무제한 스트림 중에서 앞에 10개 빼고 최대 10개 까지만");
        // TODO
        Stream.iterate(10, i -> i + 1) // 이렇게 까지만 하면 무제한 스트림이 생성된다. (물론 중개형 opertor 이므로 실행은 안함.)
                .skip(10)
                .limit(10)
                .forEach(System.out::println);

        System.out.println("자바 수업 중에 Test가 들어있는 수업이 있는지 확인");
        // TODO
        boolean test = javaClasses.stream()
                .anyMatch(onlineClass -> onlineClass.getTitle().contains("Test"));
        System.out.println(test);

        System.out.println("스프링 수업 중에 제목에 spring이 들어간 제목만 모아서 List로 만들기");
        // TODO
        List<String> spring = springClasses.stream()
                .map(OnlineClass::getTitle)
                .filter(title -> title.contains("spring"))
                .collect(Collectors.toList());

        spring.forEach(System.out::println);
    }

}
