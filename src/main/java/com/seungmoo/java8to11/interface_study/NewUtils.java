package com.seungmoo.java8to11.interface_study;

import java.util.*;
import java.util.stream.Collectors;

public class NewUtils {

    /**
     * Iterable의 기본 메소드
     * •	forEach()
     * •	spliterator()
     */
    public void runIterator() {
        List<String> names = new ArrayList<>();
        names.add("seungmoo");
        names.add("sam");
        names.add("toby");
        names.add("spring");

        // forEach : default method (Iterable)
        names.forEach(System.out::println);

        // spliterator : default method (Iterable)
        // iterator와 비슷하나 split 하는 기능을 갖고 있다.
        Spliterator<String> spliterator = names.spliterator();
        // spliterator에서 trySplit()을 실행하면 위의 Spliterator와 반반씩 나눈다.
        // Parellel 하게 작업할 때 쓰면 유용할 수 있다.
        Spliterator<String> spliterator1 = names.spliterator().trySplit();
        // tryAdvance로 순회하면서 메서드 레퍼런스 실행한다.
        while (spliterator.tryAdvance(System.out::println));
        while (spliterator1.tryAdvance(System.out::println));

    }

    /**
     * Collection의 기본 메소드
     * •	stream() / parallelStream()
     * •	removeIf(Predicate)
     * •	spliterator()
     */
    public void runCollection() {
        List<String> names = new ArrayList<>();
        names.add("seungmoo");
        names.add("sam");
        names.add("toby");
        names.add("spring");

        // Collection에서 stream()은 spliterator()를 사용한다. (소스 들어가보면 나옴)
        names.stream().map(String::toUpperCase)
                .filter(s -> s.startsWith("K"))
                .collect(Collectors.toSet());

        // removeIf
        names.removeIf(s -> s.startsWith("k"));
        names.forEach(System.out::println);

    }

    /**
     * Comparator의 기본 메소드 및 스태틱 메소드
     * •	reversed()
     * •	thenComparing()
     * •	static reverseOrder() / naturalOrder()
     * •	static nullsFirst() / nullsLast()
     * •	static comparing()
     */
    public void runComparator() {
        List<String> names = new ArrayList<>();
        names.add("seungmoo");
        names.add("sam");
        names.add("toby");
        names.add("spring");


        Comparator<String> compareToIgnoreCase = String::compareToIgnoreCase;
        Comparator<String> compareTo = String::compareTo;
        // thenComparing으로 연속 비교 가능함.
        names.sort(compareToIgnoreCase.reversed().thenComparing(compareTo));
    }

}
