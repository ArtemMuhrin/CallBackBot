package com.example.callbackbot.service;

import com.example.callbackbot.aspect.LogMethodCallCount;
import com.example.callbackbot.model.CallbackEvent;
import com.example.callbackbot.util.CallbackEventProcessor;
import com.example.callbackbot.util.CallbackEventProcessorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class CallbackService {

    private static final Logger logger = LoggerFactory.getLogger(CallbackService.class);
    private final CallbackEventProcessorFactory callbackEventProcessorFactory;

    public CallbackService(CallbackEventProcessorFactory callbackEventProcessorFactory) {
        this.callbackEventProcessorFactory = callbackEventProcessorFactory;
    }

    @LogMethodCallCount
    public String processCallbackEvent(CallbackEvent callbackEvent) {
        CallbackEventProcessor callbackEventProcessor = callbackEventProcessorFactory.getEventProcessor(callbackEvent.getType());
        if (callbackEventProcessor == null) {
            logger.warn("failed to create message processor.");
            return "Something went wrong.";
        } else {
            return callbackEventProcessor.process(callbackEvent);
        }
    }
}
