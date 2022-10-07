package com.stussy.stussyclone20220930syw.aop.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
//실행중에 쓸거다 , TYPE은 클래스 , 클래스위에 달아도된다  METHOD위에 달아도된다.
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface LogAspect {

}
