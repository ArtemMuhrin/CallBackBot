package com.example.callbackbot.kafka;

import com.example.callbackbot.model.Message;
import com.example.callbackbot.service.CallbackService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private final CallbackService callbackService;

    public KafkaConsumer(CallbackService callbackService) {
        this.callbackService = callbackService;
    }

    @KafkaListener(topics = "response", groupId = "group_id", containerFactory = "kafkaListener")
    public void consume(Message message) {
        callbackService.sendCallbackMessage(callbackService.buildCallbackMessage(message));
    }
}
