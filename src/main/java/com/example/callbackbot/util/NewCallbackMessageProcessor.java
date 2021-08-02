package com.example.callbackbot.util;

import com.example.callbackbot.model.CallbackEvent;
import com.example.callbackbot.kafka.KafkaProducer;
import com.example.callbackbot.model.Group;
import com.example.callbackbot.model.Message;
import com.example.callbackbot.repository.GroupRepository;
import com.example.callbackbot.service.KeyboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NewCallbackMessageProcessor implements CallbackEventProcessor {

    private static final Logger logger = LoggerFactory.getLogger(NewCallbackMessageProcessor.class);
    private final KafkaProducer kafkaProducer;
    private final GroupRepository groupService;
    private final KeyboardService keyboardService;

    public NewCallbackMessageProcessor(KafkaProducer kafkaProducer, GroupRepository groupService, KeyboardService keyboardService) {
        this.kafkaProducer = kafkaProducer;
        this.groupService = groupService;
        this.keyboardService = keyboardService;
    }

    @Override
    public String process(CallbackEvent callbackEvent) {
        Group group = groupService.findByGroupId(callbackEvent.getGroupId());
        if (group == null) {
            logger.warn("group not found");
        } else if (!group.getActive()) {
            logger.warn("bot is not active");
        } else {
            Map<String, Object> map = (Map<String, Object>) callbackEvent.getObject().get("message");
            Message message = Message.builder()
                    .clientId(Long.parseLong(String.valueOf(map.get("peer_id"))))
                    .groupId(callbackEvent.getGroupId())
                    .text(String.valueOf(map.get("text")))
                    .build();
            if(map.containsKey("payload")) {
                keyboardService.handleButtonClick(message);
            } else {
                kafkaProducer.send(message);
            }
        }
        return "OK";
    }
}