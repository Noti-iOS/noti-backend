package com.noti.noti.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


/**
 * @description 실행시간을 측정하기 위한 AOP 구현 class
 * @author  임호준
 * @since   2023-01-19
 * @updated
 *
 */

@Aspect
@Component
@Slf4j
public class ExecutionTimeAop {

  // com.noti 하위 패키지의 Controller 로 끝나는 클래스의 모든 메서드의 point cut
  @Pointcut("execution(* com.noti..*Controller.*(..))")
  public void cut() {}

  // controller 와 @ExecutionTimeChecker 어노테이션을 붙인 클래스나 메서드에서 작동
 @Around("cut() || @within(com.noti.noti.aop.ExecutionTimeChecker)")
  public Object calculateExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
    // 해당 클래스 처리 전의 시간
    StopWatch sw = new StopWatch();
    sw.start();

    // 해당 클래스의 메소드 실행
    Object result = pjp.proceed();

    // 해당 클래스 처리 후의 시간
    sw.stop();
    long executionTime = sw.getTotalTimeMillis();

    String className = pjp.getTarget().getClass().getName();
    String methodName = pjp.getSignature().getName();
    String task = className + "." + methodName;

    log.info("[ExecutionTime] " + task + "-->" + executionTime + "(ms)");

    return result;
  }
}
