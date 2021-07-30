package com.example.callbackbot.util;

import com.example.callbackbot.dto.CallbackEventDto;
import org.springframework.stereotype.Component;

@Component
public class CallbackEventProcessorFactory {

    private final NewCallbackMessageProcessor newCallbackMessageProcessor;
    private final ConfirmationProcessor confirmationProcessor;
    private final EventMessageProcessor eventMessageProcessor;
    private final ReplyMessageProcessor replyMessageProcessor;

    public CallbackEventProcessorFactory(NewCallbackMessageProcessor newCallbackMessageProcessor,
                                         ConfirmationProcessor confirmationProcessor,
                                         EventMessageProcessor eventProcessor,
                                         ReplyMessageProcessor replyMessageProcessor) {
        this.newCallbackMessageProcessor = newCallbackMessageProcessor;
        this.confirmationProcessor = confirmationProcessor;
        this.eventMessageProcessor = eventProcessor;
        this.replyMessageProcessor = replyMessageProcessor;
    }

    public CallbackEventProcessor getEventProcessor(CallbackEventDto.EventType eventType) {
        switch (eventType) {
            case message_new:
                return newCallbackMessageProcessor;
            case message_event:
                return eventMessageProcessor;
            case confirmation:
                return confirmationProcessor;
            case message_reply:
                return replyMessageProcessor;
            default:
                return null;
        }
    }
}
