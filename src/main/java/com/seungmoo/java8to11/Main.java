package com.seungmoo.java8to11;

import com.seungmoo.java8to11.interface_study.DefaultFoo;
import com.seungmoo.java8to11.interface_study.Foo;
import com.seungmoo.java8to11.lambda.LambdaApplication;
import com.seungmoo.java8to11.optional_study.TestOptional;
import com.seungmoo.java8to11.stream_study.StreamFoo;

public class Main {
    public static void main(String[] args) {
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

        TestOptional testOptional = new TestOptional();
        testOptional.runOptional();
    }
}

