package com.fundoonotes.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.fundoonotes.service..*(..))")
    public void logBeforeServiceMethod(JoinPoint joinPoint) {
        log.info("[AOP] Entering: {}", joinPoint.getSignature().toShortString());
    }

    @After("execution(* com.fundoonotes.service..*(..))")
    public void logAfterServiceMethod(JoinPoint joinPoint) {
        log.info("[AOP] Exiting: {}", joinPoint.getSignature().toShortString());
    }

    @Around("execution(* com.fundoonotes.service..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        log.info("[AOP] {} executed in {} ms", joinPoint.getSignature().toShortString(), (end - start));
        return result;
    }

    @AfterThrowing(pointcut = "execution(* com.fundoonotes.service..*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        log.error("[AOP] Exception in {}: {}", joinPoint.getSignature().toShortString(), ex.getMessage());
    }
}
