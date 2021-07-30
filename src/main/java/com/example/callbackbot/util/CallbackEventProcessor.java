package com.example.callbackbot.util;

import com.example.callbackbot.dto.CallbackEventDto;
import org.springframework.stereotype.Component;

@Component
public interface CallbackEventProcessor {
    String process(CallbackEventDto callbackEventDto);
}
