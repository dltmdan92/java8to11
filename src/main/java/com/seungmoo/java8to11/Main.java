package com.seungmoo.java8to11;

import com.seungmoo.java8to11.concurrent.CallableFuture;
import com.seungmoo.java8to11.concurrent.ThreadStudy;
import com.seungmoo.java8to11.date_study.DateApi;
import com.seungmoo.java8to11.date_study.DateApi2;
import com.seungmoo.java8to11.interface_study.DefaultFoo;
import com.seungmoo.java8to11.interface_study.Foo;
import com.seungmoo.java8to11.lambda.LambdaApplication;
import com.seungmoo.java8to11.optional_study.TestOptional;
import com.seungmoo.java8to11.stream_study.StreamFoo;

import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //LambdaApplication.lambda1();
        //LambdaApplication.lambda2();
        //LambdaApplication.메소드_레퍼런스();

        /*
        Foo foo = new DefaultFoo("seungmoo");
        foo.printNameUpperCase();

        // static method를 쓰는 방법
        Foo.printAnything();

         */

        /*
        StreamFoo foo = new StreamFoo();
        foo.runStream();
        */

        /*
        TestOptional testOptional = new TestOptional();
        testOptional.runOptional();
        */

        /*
        DateApi dateApi = new DateApi();
        try {
            dateApi.runDate();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */

        /*
        DateApi2 dateApi2 = new DateApi2();
        dateApi2.runDateApi();
        System.out.println("==========================");
        dateApi2.runDuration();
        System.out.println("==========================");
        dateApi2.runLocalDateTime();
        */

        /*
        ThreadStudy threadStudy = new ThreadStudy();
        threadStudy.joinThread();
        */

        CallableFuture callableFuture = new CallableFuture();
        callableFuture.runCompletableFutrure();
    }
}

