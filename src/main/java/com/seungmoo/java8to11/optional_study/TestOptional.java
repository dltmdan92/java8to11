package com.seungmoo.java8to11.optional_study;



import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TestOptional {
    public void runOptional() {
        List<OnlineClass> springClasses = new ArrayList<>();
        springClasses.add(new OnlineClass(1, "spring boot", true));
        springClasses.add(new OnlineClass(2, "spring data jpa", true));
        springClasses.add(new OnlineClass(3, "spring mvc", false));
        springClasses.add(new OnlineClass(4, "spring core", false));
        springClasses.add(new OnlineClass(5, "rest api development", false));

        OnlineClass spring_boot = new OnlineClass(1, "spring boot", true);

        Optional<Progress> progress = spring_boot.getProgress();
        progress.ifPresent(prog -> System.out.println(prog.getStudyDuration()));

        Optional<OnlineClass> springOc = springClasses.stream()
                .filter(onlineClass -> onlineClass.getTitle().startsWith("spring"))
                // .findFirst()의 return Type은 Optional이다.
                .findFirst();

        boolean present = springOc.isPresent(); // java11 부터 isEmpty()도 제공한다.
        System.out.println(present);

        // 비어 있는 Optional에서 바로 get()할 경우 NoSuchElementException (RuntimeException) 발생한다.
        // if 문으로 present / empty 체크하지 말고 Optional API 를 활용해보자 (ifPresent)
        //OnlineClass onlineClass = springOc.get();
        springOc.ifPresent(oc -> System.out.println(oc.getTitle()));

        // orElse 있으면 그거 꺼내고, 없으면 새로운 객체 만들어서 리턴한다.
        // Optional 객체가 notEmpty, 존재하더라도 orElse() 안에 있는 로직은 실행이 된다.
        // --> orElseGet()을 사용하면 필요할 때만 실행 (Lazy)
        OnlineClass onlineClass = springOc.orElse(createNewClass());
        System.out.println(onlineClass.getTitle());

        // orElseGet 안에 있는 녀석은 Lazy 한 방식으로 실행된다.
        OnlineClass onlineClass1 = springOc.orElseGet(TestOptional::createNewClass);
        // orElseThrow로 Exception을 던질 수 있다.
        OnlineClass onlineClass2 = springOc.orElseThrow(IllegalStateException::new);

        /**
         * filter의 결과물은 Optional Type임.
         */

        // map을 통해 Optional<T>의 T타입을 바꿔서 리턴할 수 있다. Optioanl
        Optional<Integer> ocTitle = springOc.map(OnlineClass::getId);

        // 이건 좋지 않다... 중첩 Optional 타입은 안좋음.
        Optional<Optional<Progress>> progress1 = springOc.map(OnlineClass::getProgress);

        // 위와 같은 경우에는 flatMap을 써주면 좋다.
        // 참고로 stream의 flatMap과는 엄연히 다르다!!
        Optional<Progress> progressOptional = springOc.flatMap(OnlineClass::getProgress);
    }

    private static OnlineClass createNewClass() {
        System.out.println("creating new online class");
        return new OnlineClass(10, "New class", false);
    }
}
