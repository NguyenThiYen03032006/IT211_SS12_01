package com.it211_ss12_01.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.example.demo.controller.BookController.*(..))")
    public void logBeforeController(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        System.out.println("[Bước 1 - @Before] Tên method: " + methodName + " | Tham số: " + args);
    }

    @AfterReturning(pointcut = "execution(* com.example.demo.service.BookService.*(..))", returning = "result")
    public void logAfterService(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("[Bước 2 - @AfterReturning] Tên method: " + methodName + " | Kết quả: " + result);
    }

    @Around("execution(* com.example.demo.controller.BookController.*(..))")
    public Object logAroundController(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        String methodName = joinPoint.getSignature().getName();
        System.out.println("[Bước 3 - @Around] Method: " + methodName + " | Thời gian thực thi: " + executionTime + " ms");
        return proceed;
    }
}