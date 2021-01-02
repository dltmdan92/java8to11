package com.seungmoo.java8to11.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
//@Target(ElementType.TYPE_PARAMETER) // TYPE_PARAMETER : TYPE_PARAMETER에만 애노테이션 사용
@Target(ElementType.TYPE_USE) // Type을 사용하는 모든 곳에 가능
@Repeatable(ChickenContainer.class) // Annotation을 중복 셋팅해줄 수 있도록 한다. (Container annotation 필요)
public @interface Chicken {

    // 중복 선언되는 Annotation은 각자 value값을 담고 있어야 한다.
    String value();
}
