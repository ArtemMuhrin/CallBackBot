package com.example.callbackbot.util;

import com.example.callbackbot.dto.CallbackEventDto;
import org.springframework.stereotype.Component;

@Component
public class EventMessageProcessor implements CallbackEventProcessor{
    @Override
    public String process(CallbackEventDto callbackEventDto) {
        return "OK";
    }
}
