package com.noti.noti.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description 실행시간을 측정하고 싶으면 클래스나 메서드에 해당 어노테이션을 붙여서 사용
 * @author 임호준
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface ExecutionTimeChecker {

}
