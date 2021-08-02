package com.example.callbackbot.service;

import com.example.callbackbot.model.CallbackEvent;
import com.example.callbackbot.model.CallbackMessage;
import com.example.callbackbot.model.CallbackResponse;
import com.example.callbackbot.model.Group;
import com.example.callbackbot.model.Message;
import com.example.callbackbot.util.CallbackEventProcessor;
import com.example.callbackbot.util.CallbackEventProcessorFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@Service
public class CallbackService {

    private static final Logger logger = LoggerFactory.getLogger(CallbackService.class);
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final GroupService groupService;
    private final CallbackEventProcessorFactory callbackEventProcessorFactory;

    @Value("https://api.vk.com/method/messages.send")
    private String sendUrl;

    public CallbackService(ObjectMapper objectMapper, RestTemplate restTemplate, GroupService groupService, CallbackEventProcessorFactory callbackEventProcessorFactory) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
        this.groupService = groupService;
        this.callbackEventProcessorFactory = callbackEventProcessorFactory;
    }

    public String processCallbackEvent(CallbackEvent callbackEvent) {
        CallbackEventProcessor callbackEventProcessor = callbackEventProcessorFactory.getEventProcessor(callbackEvent.getType());
        return callbackEventProcessor.process(callbackEvent);
    }

    public void sendCallbackMessage(CallbackMessage callbackMessage) {
        try {
            URI uri = createUri(callbackMessage);
            ResponseEntity<CallbackResponse> responseEntity = restTemplate.postForEntity(uri, null, CallbackResponse.class);
            if (responseEntity.getBody().getError() != null) {
                logger.warn(responseEntity.getBody().getError().getErrorMsg());
            }
        } catch (JsonProcessingException e) {
            logger.warn(e.getMessage());
        }
    }

    public CallbackMessage buildCallbackMessage(Message message) {
        Group group = groupService.findByGroupId(message.getGroupId());
        return CallbackMessage.builder()
                .peerId(message.getClientId())
                .message(message.getText())
                .groupId(message.getGroupId())
                .token(group.getToken())
                .version(group.getVersion())
                .randomId((long) message.hashCode())
                .keyboard(message.getKeyboard())
                .build();
    }

    private URI createUri(CallbackMessage callbackMessage) throws JsonProcessingException {
        MultiValueMap<String, String> map = objectMapper.convertValue(callbackMessage, LinkedMultiValueMap.class);
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(sendUrl).queryParams(map);
        if (!(callbackMessage.getKeyboard() == null)) {
            uriComponentsBuilder.queryParam("keyboard", objectMapper.writeValueAsString(callbackMessage.getKeyboard()));
        }
        return uriComponentsBuilder.build().toUri();
    }
}
