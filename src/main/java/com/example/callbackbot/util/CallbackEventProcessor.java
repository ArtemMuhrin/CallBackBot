package com.example.callbackbot.util;

import com.example.callbackbot.model.CallbackEvent;
import org.springframework.stereotype.Component;

@Component
public interface CallbackEventProcessor {
    String process(CallbackEvent callbackEvent);
}
