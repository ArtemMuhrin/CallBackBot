package com.example.callbackbot.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class IncreaseCounterJob {

    private final MeterRegistry meterRegistry;

    public IncreaseCounterJob(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Scheduled(fixedDelay = 1000)
    public void increaseMethodsCallCounter(String methodName) {
        meterRegistry.counter("bot.methods.call", "method", methodName).increment();
    }
}