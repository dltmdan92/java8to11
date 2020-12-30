package com.seungmoo.java8to11.stream_study;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

}
