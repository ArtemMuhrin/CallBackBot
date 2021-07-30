package com.example.callbackbot.kafka;

import com.example.callbackbot.dto.CallbackMessageSendDto;
import com.example.callbackbot.model.Group;
import com.example.callbackbot.model.Message;
import com.example.callbackbot.service.CallbackService;
import com.example.callbackbot.service.GroupService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private final CallbackService callbackService;
    private final GroupService groupService;

    public KafkaConsumer(CallbackService callbackService, GroupService groupService) {
        this.callbackService = callbackService;
        this.groupService = groupService;
    }

    @KafkaListener(topics = "response", groupId = "group_id", containerFactory = "kafkaListener")
    public void consume(Message message) {
        Group group = groupService.findByGroupId(message.getGroupId());
        CallbackMessageSendDto callbackMessageSendDto = CallbackMessageSendDto.builder()
                .peerId(message.getClientId())
                .message(message.getText())
                .groupId(message.getGroupId())
                .token(group.getToken())
                .version(group.getVersion())
                .randomId((long) message.hashCode())
                .build();
        callbackService.sendCallbackMessage(callbackMessageSendDto);
    }
}
