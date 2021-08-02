package com.example.callbackbot.service;

import com.example.callbackbot.model.CallbackMessage;
import com.example.callbackbot.model.Group;
import com.example.callbackbot.model.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KeyboardService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final GroupService groupService;
    private final CallbackService callbackService;
    private final CircuitBreakerFactory circuitBreakerFactory;

    @Value("${keyboard.server.host}/keyboard/key")
    String getKeyboardUrl;

    public KeyboardService(GroupService groupService, @Lazy CallbackService callbackService, CircuitBreakerFactory circuitBreakerFactory) {
        this.groupService = groupService;
        this.callbackService = callbackService;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    public void handleButtonClick(Message message) {
        String response = sendRequest(message.getText());
        message.setText(response);
        Group group = groupService.findByGroupId(message.getGroupId());
        CallbackMessage callbackMessage = CallbackMessage.builder()
                .peerId(message.getClientId())
                .message(message.getText())
                .groupId(message.getGroupId())
                .token(group.getToken())
                .version(group.getVersion())
                .randomId((long) message.hashCode())
                .keyboard(message.getKeyboard())
                .build();
        callbackService.sendCallbackMessage(callbackMessage);
    }

    private String sendRequest(String value) {
        return circuitBreakerFactory.create("circuitbreaker").run(() -> restTemplate.postForEntity(getKeyboardUrl, value, String.class).getBody(), throwable -> "мне плохо");
    }
}
