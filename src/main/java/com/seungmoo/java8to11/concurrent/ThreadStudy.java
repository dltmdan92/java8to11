package com.seungmoo.java8to11.concurrent;

/**
 * 아래를 코드들을 보면 Thread 처리 소스가 매우 복잡하다 --> Executor를 활용해보자
 */
public class ThreadStudy {

    public void runThread() throws InterruptedException {
        /*
        MyThread myThread = new MyThread();
        myThread.start();
         */

        /**
         * Runnable이 FunctionalInterface 이기 때문에 람다식으로 사용할 수 있다.
         */
        Thread runnableThread = new Thread(() -> {
            try {
                // 이렇게 해주면 다른 쓰레드에게 우선권이 간다. (sleep 되는 동안 Main 쓰레드가 실행될 것임)
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        runnableThread.start();

        System.out.println("Hello : " + Thread.currentThread().getName());
    }

    /**
     * interrupt()를 사용해서 쓰레드를 종료 시킨다.
     * @throws InterruptedException
     */
    public void interruptThread() throws InterruptedException {
        Thread runnableThread = new Thread(() -> {
            while (true) {
                System.out.println("Runnable Thread : " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    // InterruptedException을 받으면 쓰레드 종료.
                    System.out.println("exit!");
                    return; // 쓰레드 종료!
                }
            }
        });

        runnableThread.start();

        System.out.println("Hello : " + Thread.currentThread().getName());
        Thread.sleep(3000L);
        runnableThread.interrupt();
    }

    /**
     * join : 쓰레드를 기다린다.
     *
     */
    public void joinThread()  {
        Thread runnableThread = new Thread(() -> {
            System.out.println("Runnable Thread : " + Thread.currentThread().getName());
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                // InterruptedException을 받으면 쓰레드 종료.
                System.out.println("InterruptedException!");
            }
        });
        runnableThread.start();

        System.out.println("Hello : " + Thread.currentThread().getName());
        try {
            // 위의 쓰레드를 기다린다. (Thread.sleep(3000L);)
            runnableThread.join();
        } catch (InterruptedException e) {
            // 쓰레드 기다리다가 interrupt 발생 대비해서 또 exception 처리해줘야 한다.
            e.printStackTrace();
        }
        System.out.println("After Join : " + Thread.currentThread().getName());
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Thread : " + Thread.currentThread().getName());
        }
    }

}
