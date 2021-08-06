package com.example.callbackbot.service;

import com.example.callbackbot.model.CallbackEvent;
import com.example.callbackbot.util.CallbackEventProcessor;
import com.example.callbackbot.util.CallbackEventProcessorFactory;
import org.springframework.stereotype.Service;


@Service
public class CallbackService {

    private final CallbackEventProcessorFactory callbackEventProcessorFactory;

    public CallbackService(CallbackEventProcessorFactory callbackEventProcessorFactory) {
        this.callbackEventProcessorFactory = callbackEventProcessorFactory;
    }

    public String processCallbackEvent(CallbackEvent callbackEvent) {
        CallbackEventProcessor callbackEventProcessor = callbackEventProcessorFactory.getEventProcessor(callbackEvent.getType());
        return callbackEventProcessor.process(callbackEvent);
    }
}
