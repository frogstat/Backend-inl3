package se.yrgo.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component("PerformanceTimingAdvice")
public class PerformanceTimingAdvice{

    @Around("execution(* se.yrgo.dataaccess.*.*(..)) || execution(* se.yrgo.services.*.*.*(..))")
    public Object performTimingMeasurement(ProceedingJoinPoint method) throws Throwable {
        long startTime = System.currentTimeMillis();

        try {
            return method.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            long timeTaken = endTime - startTime;
            System.out.printf("MEASUREMENT: Time taken for the method %s from the class %s took %dms%n",
                    method.getSignature().getName(), method.getSignature().getDeclaringTypeName(), timeTaken);
        }
    }
}
