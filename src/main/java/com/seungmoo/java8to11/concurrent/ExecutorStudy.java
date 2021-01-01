package com.seungmoo.java8to11.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <Executors>
 * 고수준 (High-Level) Concurrency 프로그래밍
 * •	쓰레드를 만들고 관리하는 작업을 애플리케이션에서 분리.
 * •	그런 기능을 Executors에게 위임.
 *
 * Executors가 하는 일
 * •	쓰레드 만들기: 애플리케이션이 사용할 쓰레드 풀을 만들어 관리한다.
 * •	쓰레드 관리: 쓰레드 생명 주기를 관리한다.
 * •	작업 처리 및 실행: 쓰레드로 실행할 작업을 제공할 수 있는 API를 제공한다.
 *
 * 주요 인터페이스
 * •	Executor: execute(Runnable)
 * •	ExecutorService: Executor 상속 받은 인터페이스로, Callable도 실행할 수 있으며, Executor를 종료 시키거나, 여러 Callable을 동시에 실행하는 등의 기능을 제공한다.
 * •	ScheduledExecutorService: ExecutorService를 상속 받은 인터페이스로 특정 시간 이후에 또는 주기적으로 작업을 실행할 수 있다.
 *
 * Runnable은 void 이다. 결과물을 받을 수 없음
 * Callable은 결과물을 받을 수 있다. (제네릭스 타입을 리턴할 수 있다.) --> 그것의 return이 Future 이다.
 */
public class ExecutorStudy {

    public void runExecutorService() {
        // 싱글스레드
        //ExecutorService executorService = Executors.newSingleThreadExecutor();

        // 쓰레드 풀 (쓰레드 갯수 2개)
        // 쓰레드 풀 과 Blocking Queue (쓰레드 풀에 들어가지 못한 Task들을 Queue에 담아 놓고, 비어있는 Thread에 할당 해준다.)
        // 쓰레드 풀의 장점 : 쓰레드를 매번 생성하는 비용을 줄 일 수 있다.
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        // 5개의 작업을 두 개의 쓰레드를 번갈아 사용하면서 수행한다. (Blocking Queue를 통해 작업 대기 및 쓰레드 할당 해준다.)
        executorService.submit(getRunnable("Hello"));
        executorService.submit(getRunnable("Keesun"));
        executorService.submit(getRunnable("The"));
        executorService.submit(getRunnable("Java"));
        executorService.submit(getRunnable("Thread"));

        // ExecutorService는 명시적으로 shutdown을 해줘야 한다.
        // 이 shutdown은 graceful shutdown 이다.
        // - graceful shutdown : 현재 진행 중인 작업을 끝까지 마치고 shutdown 한다는 것 (명예로운 shutdown?)
        executorService.shutdown();

        // Non graceful shutdown 이다.
        //executorService.shutdownNow();
    }

    public void runScheduledExecutorService() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        // 3초 기다림
        executorService.schedule(getRunnable("Hello"), 3, TimeUnit.SECONDS);
        // 1초 기다렸다가 2초 주기로 쓰레드 실행
        executorService.scheduleAtFixedRate(getRunnable("Hello"), 1, 2, TimeUnit.SECONDS);

        // 여기서 shutdown 하게 되면 기다리다가 Thread가 끝나게 된다.
        //executorService.shutdown();
    }

    private Runnable getRunnable(String message) {
        return () -> {
            System.out.println("message : " + Thread.currentThread().getName());
        };
    }

}
