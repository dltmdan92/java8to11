package com.seungmoo.java8to11.concurrent;

import java.util.List;
import java.util.concurrent.*;

/**
 * Callable
 * •	Runnable과 유사하지만 작업의 결과를 받을 수 있다.
 *
 * Future
 * •	비동기적인 작업의 현재 상태를 조회하거나 결과를 가져올 수 있다.
 * •	https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Future.html
 */
public class CallableFuture {

    public void runCallable() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> hello = () -> {
            Thread.sleep(2000L);
            return "hello";
        };

        Future<String> helloFuture = executorService.submit(hello);
        System.out.println(helloFuture.isDone()); // false
        System.out.println("Started");

        /*
        작업 취소하기 cancel()
        •	취소 했으면 true 못했으면 false를 리턴한다.
        •	parameter로 true를 전달하면 현재 진행중인 쓰레드를 interrupt하고 그러지 않으면 현재 진행중인 작업이 끝날때까지 기다린다.
         */
        // true : 작업을 interrupt해서 cancel 한다.
        // false : 작업을 기다림.
        // false(작업 기다림)를 해도 일단 cancel을 호출하면 get() 을 할 수 없다. (에러 발생)
        // 일단 cancel을 하면 isDone은 true가 된다.
        //helloFuture.cancel(true);

        // cancel 메소드 호출 되면 get() 하면 오류 발생함.
        // get 이전까지는 안 기다리고 그냥 코드 실행 됨.
        // get을 만나는 순간 멈추고 thread의 결과값을 기다린다. == blocking call
        helloFuture.get();

        System.out.println(helloFuture.isDone()); // true : 작업이 종료되었다. (작업 완수 되거나 or 작업 cancel 되어서 종료)
        System.out.println("End!!");
        executorService.shutdown();
    }

    // 한 방에 여러개 돌리는 법
    public void runCallable2() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Callable<String> hello = () -> {
            Thread.sleep(2000L);
            return "hello";
        };

        Callable<String> java = () -> {
            Thread.sleep(3000L);
            return "Java";
        };

        Callable<String> seungmoo = () -> {
            Thread.sleep(1000L);
            return "Seungmoo";
        };

        // invokeAll : 등록된 작업들을 모두 기다린다. java 작업 3초까지 기다려 준 후, 모든 Callable Task를 get()함.
        /*
        여러 작업 동시에 실행하기 invokeAll()
        •	동시에 실행한 작업 중에 제일 오래 걸리는 작업 만큼 시간이 걸린다.
         */
        List<Future<String>> futures = executorService.invokeAll(List.of(hello, java, seungmoo));
        for (Future<String> f : futures) {
            System.out.println(f.get());
        }

        /*
        여러 작업 중에 하나라도 먼저 응답이 오면 끝내기 invokeAny()
        •	동시에 실행한 작업 중에 제일 짧게 걸리는 작업 만큼 시간이 걸린다.
        •	블록킹 콜이다.

        여기서는 그러면 Seungmoo가 출력되고 끝날 것임.
         */
        String s = executorService.invokeAny(List.of(hello, java, seungmoo));
        System.out.println(s);

        executorService.shutdown();
    }

}

