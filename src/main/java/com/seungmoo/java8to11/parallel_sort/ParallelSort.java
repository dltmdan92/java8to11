package com.seungmoo.java8to11.parallel_sort;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * 20.	배열 Parallel 정렬
 * Arrays.parallelSort()
 * •	Fork/Join 프레임워크를 사용해서 배열을 병렬로 정렬하는 기능을 제공한다.
 *
 * 병렬 정렬 알고리듬
 * •	배열을 둘로 계속 쪼갠다.
 * •	합치면서 정렬한다. (머지소트??)
 *
 * 경우에 따라 parallelSort와 serialSort의 속도 차이가 다르게 나옴
 * 숫자가 커질수록 parallelSort가 느린거 같긴한데...
 */
public class ParallelSort {
    public static void run() {
        int size = 1500;
        int[] numbers = new int[size];
        Random random = new Random();
        IntStream.range(0, size).forEach(i -> numbers[i] = random.nextInt());

        long start = System.nanoTime();
        Arrays.sort(numbers);
        System.out.println("serial sorting took " + (System.nanoTime() - start));

        IntStream.range(0, size).forEach(i -> numbers[i] = random.nextInt());
        start = System.nanoTime();
        Arrays.parallelSort(numbers);
        System.out.println("parallel sorting took " + (System.nanoTime() - start));

    }
}
