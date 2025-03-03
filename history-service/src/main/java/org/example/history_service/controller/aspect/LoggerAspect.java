package org.example.history_service.controller.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LoggerAspect {

    //Specifies which methods should trigger this aspect.
    // (||) Ensures that methods from any of the three controllers will trigger the logging.
    @Before("execution(* org.example.history_service.controller.HistoryController.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        //Retrieves the name of the method being executed.
        String methodName = joinPoint.getSignature().getName();
        log.info("Method executed: {}", methodName);
    }
}