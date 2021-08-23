package com.example.callbackbot.aspect;

import com.example.callbackbot.metrics.IncreaseCounterJob;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MetricsAspect {

    private final IncreaseCounterJob increaseCounterJob;

    public MetricsAspect(IncreaseCounterJob increaseCounterJob) {
        this.increaseCounterJob = increaseCounterJob;
    }

    @Before("@annotation(LogMethodCallCount)")
    public void increaseMethodsCallCounter(JoinPoint joinPoint) throws Throwable {
        increaseCounterJob.increaseMethodsCallCounter(joinPoint.getSignature().getName());
    }
}
