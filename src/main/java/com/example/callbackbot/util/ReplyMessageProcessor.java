package com.example.callbackbot.util;

import com.example.callbackbot.aspect.LogMethodCallCount;
import com.example.callbackbot.model.CallbackEvent;
import org.springframework.stereotype.Component;

@Component
public class ReplyMessageProcessor implements CallbackEventProcessor {

    @LogMethodCallCount
    @Override
    public String process(CallbackEvent callbackEvent) {
        return "OK";
    }
}
