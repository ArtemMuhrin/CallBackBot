package com.example.callbackbot.util;

import com.example.callbackbot.model.CallbackEvent;
import org.springframework.stereotype.Component;

@Component
public class CallbackEventProcessorFactory {

    private final NewCallbackMessageProcessor newCallbackMessageProcessor;
    private final ConfirmationProcessor confirmationProcessor;
    private final ReplyMessageProcessor replyMessageProcessor;

    public CallbackEventProcessorFactory(NewCallbackMessageProcessor newCallbackMessageProcessor,
                                         ConfirmationProcessor confirmationProcessor,
                                         ReplyMessageProcessor replyMessageProcessor) {
        this.newCallbackMessageProcessor = newCallbackMessageProcessor;
        this.confirmationProcessor = confirmationProcessor;
        this.replyMessageProcessor = replyMessageProcessor;
    }

    public CallbackEventProcessor getEventProcessor(CallbackEvent.EventType eventType) {
        switch (eventType) {
            case message_new:
                return newCallbackMessageProcessor;
            case confirmation:
                return confirmationProcessor;
            case message_reply:
                return replyMessageProcessor;
            default:
                return null;
        }
    }
}
