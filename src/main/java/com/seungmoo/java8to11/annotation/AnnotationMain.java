package com.seungmoo.java8to11.annotation;

import java.util.Arrays;
import java.util.List;

@Chicken("양념")
@Chicken("마늘간장")
@Chicken("후라이드")
public class AnnotationMain {

    public void run(@Chicken("양념") String[] args) throws @Chicken("양념") RuntimeException {
        List<@Chicken("양념") String> names = List.of("seungmoo");

        // 클래스에 선언된 중복 Annotation들을 불러오는 두 가지 방법
        // 방법 1 (그냥 Annotation 사용)
        Chicken[] chickens = AnnotationMain.class.getAnnotationsByType(Chicken.class);
        Arrays.stream(chickens).forEach(chicken -> System.out.println(chicken.value()));
        // 방법 2 (Container를 사용)
        ChickenContainer chickenContainer = AnnotationMain.class.getAnnotation(ChickenContainer.class);
        Arrays.stream(chickenContainer.value()).forEach(chicken -> System.out.println(chicken.value()));
    }

    // ElementType.TYPE_PARAMETER를 사용해서 Generics에 해당 Annotation Type이 들어올 수 있음.
    // TYPE_USE 도 가능핟.
    static class FeelsLikeChicken<@Chicken("양념") T> {

        // Type Parameter <C> 는 리턴타입 앞에 명시
        // 메서드 파라미터 타입은 TYPE_PARAMETER가 아님!!
        public static <@Chicken("양념") C> void print(
                @Chicken("양념") C c // 이거는 TYPE_USE 때문에 붙을 수 있는 거임.
        ) {
            System.out.println(c);
        }
    }

}
