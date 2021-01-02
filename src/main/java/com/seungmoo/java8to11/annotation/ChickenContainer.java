package com.seungmoo.java8to11.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Container Annotation은 자신이 감싸는 Annotation보다 범위가 같거나 넓어야 한다.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
public @interface ChickenContainer {

    // 애노테이션 중복 사용을 위해서
    // Container가 감싸는 Annotation들의 Value값들을 배열로 담고 있어야 한다.
    Chicken[] value();
}
