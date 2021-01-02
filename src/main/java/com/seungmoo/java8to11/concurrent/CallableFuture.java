package com.seungmoo.java8to11.concurrent;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

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

    /**
     * 17.	CompletableFuture 1
     * 자바에서 비동기(Asynchronous) 프로그래밍을 가능케하는 인터페이스.
     * •	Future를 사용해서도 어느정도 가능했지만 하기 힘들 일들이 많았다.
     *
     * Future(Java 5)로는 하기 어렵던 작업들
     * •	Future를 외부에서 완료 시킬 수 없다. 취소하거나, get()에 타임아웃을 설정할 수는 있다.
     * •	블로킹 코드(get())를 사용하지 않고서는 작업이 끝났을 때 콜백을 실행할 수 없다.
     * •	여러 Future를 조합할 수 없다, 예) Event 정보 가져온 다음 Event에 참석하는 회원 목록 가져오기
     * •	예외 처리용 API를 제공하지 않는다.
     *
     * CompletableFuture
     * •	Implements Future
     * •	Implements CompletionStage
     *
     * 비동기로 작업 실행하기
     * •	리턴값이 없는 경우: runAsync()
     * •	리턴값이 있는 경우: supplyAsync()
     * •	원하는 Executor(쓰레드풀)를 사용해서 실행할 수도 있다. (기본은 ForkJoinPool.commonPool())
     *
     * 콜백 제공하기
     * •	thenApply(Function): 리턴값을 받아서 다른 값으로 바꾸는 콜백
     * •	thenAccept(Consumer): 리턴값을 또 다른 작업을 처리하는 콜백 (리턴없이)
     * •	thenRun(Runnable): 리턴값 받지 다른 작업을 처리하는 콜백
     * •	콜백 자체를 또 다른 쓰레드에서 실행할 수 있다.
     *
     * 쓰레드풀을 직접 선언하지 않고 멀티쓰레드를 돌릴 수 있다.
     * java 7의 ForkJoinPool을 사용, deque를 쓴다.
     */
    public void runCompletableFutrure() throws ExecutionException, InterruptedException {
        /*
        CompletableFuture<String> future = new CompletableFuture<>();
        future.complete("seungmoo");
        */
        CompletableFuture<String> future = CompletableFuture.completedFuture("seungmoo");
        System.out.println(future.get());

        // 리턴 값 없는 Future
        CompletableFuture<Void> futureRunAsync = CompletableFuture.runAsync(() -> {
            System.out.println("Hello : " + Thread.currentThread().getName());
        });
        futureRunAsync.get();

        // 리턴 값 있는 Future
        // CompletableFuture에 쓰레드풀을 할당해줄 수 있다. (supplyAsync, thenApplyAsync, runAsync 등에 할당 가능)
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        CompletableFuture<Void> futureSupplyAsync = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello : " + Thread.currentThread().getName());
            return "Hello";
        }, executorService)
                .thenApply(s -> { // 콜백 (Function, 리턴 값 받아서 다른 값으로 바꾸고 리턴)
                    System.out.println(Thread.currentThread().getName());
                    return s.toUpperCase();
                })
                .thenAccept(s -> { // 콜백 (Consumer, 리턴 없이 처리)
                    System.out.println(Thread.currentThread().getName());
                    System.out.println(s.toUpperCase());
                })
                .thenRun(() -> { // 그냥 리턴 없이 Runnable을 돌린다.
                    System.out.println(Thread.currentThread().getName());
                });

        System.out.println(futureSupplyAsync.get());

    }

    /**
     * 18.	CompletableFuture 2
     *
     * 조합하기
     * •	thenCompose(): 두 작업이 서로 이어서 실행하도록 조합
     * •	thenCombine(): 두 작업을 독립적으로 실행하고 둘 다 종료 했을 때 콜백 실행 --> 콜백까지의 blocking이 존재
     * •	allOf(): 여러 작업을 모두 실행하고 모든 작업 결과에 콜백 실행
     * •	anyOf(): 여러 작업 중에 가장 빨리 끝난 하나의 결과에 콜백 실행
     *
     * 예외처리
     * •	exeptionally(Function)
     * •	handle(BiFunction):
     *
     * 참고
     * •	https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ForkJoinPool.html
     * •	https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void runCompletableFutrure2() throws ExecutionException, InterruptedException {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        });

        // thenCompose(): 두 작업이 서로 이어서 실행하도록 조합
        CompletableFuture<String> future = hello.thenCompose(CallableFuture::getWorld);
        System.out.println(future.get());

        // thenCombine(): 두 작업을 독립적으로 실행하고 "둘 다 종료 했을 때" 콜백 실행 (BiFunction 사용)
        CompletableFuture<String> futureCombine = hello.thenCombine(getWorld("Hello"), (helloReturn, worldReturn) -> helloReturn + " " + worldReturn);
        System.out.println(futureCombine.get());

        // allOf
        // 이거 조금 위험할 수 있음. 모든 Task가 같은 타입 보장 X, 도중에 Exception 발생 가능성도 있음
        CompletableFuture<Void> futureAllOf = CompletableFuture.allOf(hello, getWorld("Hello"))
                .thenAccept(System.out::println); // parameter가 null로 들어 오게 된다.
        System.out.println(futureAllOf.get());

        // 위의 방식을 아래 방식으로 사용해보자.
        // Type 명시 + unchecked exception으로 발생되게 처리 (join() 메서드,  (get()은 checked exception임.))
        // 아래 방식을 하면 blocking이 되지 않는다.
        List<CompletableFuture<String>> futures = List.of(hello, getWorld("Hello"));
        CompletableFuture[] futuresArray = futures.toArray(new CompletableFuture[futures.size()]);

        CompletableFuture<List<String>> listCompletableFuture = CompletableFuture.allOf(futuresArray)
                .thenApply(v -> futures.stream()
                        // get() 보다는 join()을 쓰면 unchecked exception이기 때문에 try catch 문을 생략할 수 있다.
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()));
        listCompletableFuture.get().forEach(System.out::println);

        // anyOf : 여러 작업 중에 가장 빨리 끝난 하나의 결과에 콜백 실행
        CompletableFuture<Object> futureAnyOf = CompletableFuture.anyOf(hello, getWorld("world").thenAccept(System.out::println));
        futureAnyOf.get();

        // Error를 처리하는 법
        boolean throwError = true;

        CompletableFuture<String> exceptionally = CompletableFuture.supplyAsync(() -> {
            if (throwError) {
                throw new IllegalStateException();
            }
            System.out.println("Hello Error " + Thread.currentThread().getName());
            return "Hello Error";
        })
                // Error가 발생했을 때 Error Type을 받아서 Function 실행 및 리턴
                .exceptionally(exception -> {
                    System.out.println(exception);
                    return "Error!!!";
                });
        System.out.println(exceptionally.get());

        // Result와 Error를 둘다 처리하는 법 (handle)
        CompletableFuture<Object> handle = CompletableFuture.supplyAsync(() -> {
            if (throwError) {
                throw new IllegalStateException();
            }
            System.out.println("Hello Error " + Thread.currentThread().getName());
            return "Hello Error";
        })
                .handle((result, exception) -> {
                    if (exception != null) {
                        System.out.println(exception);
                        return "ERROR !!";
                    }
                    return result;
                });
        System.out.println(handle.get());
    }

    private static CompletableFuture<String> getWorld(String message) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("World " + Thread.currentThread().getName());
            return message + " World";
        });
    }

}

