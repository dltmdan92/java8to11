package com.seungmoo.java8to11.annotation;

import java.util.Arrays;
import java.util.List;

/**
 * 19.	애노테이션의 변화
 * 애노테이션 관련 두가지 큰 변화
 * •	자바 8부터 애노테이션을 타입 선언부에도 사용할 수 있게 됨.
 * •	자바 8부터 애노테이션을 중복해서 사용할 수 있게 됨.
 *
 * 타입 선언 부
 * •	제네릭 타입
 * •	변수 타입
 * •	매개변수 타입
 * •	예외 타입
 * •	...
 *
 * 타입에 사용할 수 있으려면
 * •	TYPE_PARAMETER: 타입 변수에만 사용할 수 있다.
 * •	TYPE_USE: 타입 변수를 포함해서 모든 타입 선언부에 사용할 수 있다.
 *
 * 중복 사용할 수 있는 애노테이션을 만들기
 * •	중복 사용할 애노테이션 만들기
 * •	중복 애노테이션 컨테이너 만들기
 * o	컨테이너 애노테이션은 중복 애노테이션과 @Retention 및 @Target이 같거나 더 넓어야 한다.
 */
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

        // 타입 변수 <C> 는 리턴타입 앞에 명시
        // 메서드 파라미터 타입은 TYPE_PARAMETER가 아님!!
        public static <@Chicken("양념") C> void print(
                @Chicken("양념") C c // 이거는 TYPE_USE 때문에 붙을 수 있는 거임.
        ) {
            System.out.println(c);
        }
    }

}
