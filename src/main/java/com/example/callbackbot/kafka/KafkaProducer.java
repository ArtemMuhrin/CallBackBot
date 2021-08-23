package com.example.callbackbot.kafka;


import com.example.callbackbot.aspect.LogMethodCallCount;
import com.example.callbackbot.model.Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @LogMethodCallCount
    public void send(Message message) {
        kafkaTemplate.send("request", message);
    }
}
