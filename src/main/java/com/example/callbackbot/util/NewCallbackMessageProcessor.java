package com.example.callbackbot.util;

import com.example.callbackbot.dto.CallbackEventDto;
import com.example.callbackbot.kafka.KafkaProducer;
import com.example.callbackbot.model.Group;
import com.example.callbackbot.model.Message;
import com.example.callbackbot.repository.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NewCallbackMessageProcessor implements CallbackEventProcessor {

    private static final Logger logger = LoggerFactory.getLogger(NewCallbackMessageProcessor.class);
    private final KafkaProducer kafkaProducer;
    private final GroupRepository groupService;

    public NewCallbackMessageProcessor(KafkaProducer kafkaProducer, GroupRepository groupService) {
        this.kafkaProducer = kafkaProducer;
        this.groupService = groupService;
    }

    @Override
    public String process(CallbackEventDto callbackEventDto) {
        Group group = groupService.findByGroupId(callbackEventDto.getGroupId());
        if (group == null) {
            logger.warn("group not found");
        } else if (!group.getActive()) {
            logger.warn("bot is not active");
        } else {
            Message message = parseCallbackMessage(callbackEventDto);
            kafkaProducer.send(message);
        }
        return "OK";
    }

    private Message parseCallbackMessage(CallbackEventDto callbackEventDto) {
        Map<String, Object> callbackMessage = (Map<String, Object>) callbackEventDto.getObject().get("message");
        Message message = Message.builder()
                .clientId(Long.parseLong(String.valueOf(callbackMessage.get("peer_id"))))
                .groupId(callbackEventDto.getGroupId())
                .text(String.valueOf(callbackMessage.get("text")))
                .build();
        return message;
    }
}