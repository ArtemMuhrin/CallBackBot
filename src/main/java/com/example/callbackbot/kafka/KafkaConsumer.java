package com.example.callbackbot.kafka;

import com.example.callbackbot.aspect.LogMethodCallCount;
import com.example.callbackbot.model.Message;
import com.example.callbackbot.service.CallbackSender;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private final CallbackSender callbackSender;

    public KafkaConsumer(CallbackSender callbackSender) {
        this.callbackSender = callbackSender;
    }

    @LogMethodCallCount
    @KafkaListener(topics = "response", groupId = "group_id", containerFactory = "kafkaListener")
    public void consume(Message message) {
        callbackSender.sendMessage(message);
    }
}
