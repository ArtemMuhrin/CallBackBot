package com.example.callbackbot.service;

import com.example.callbackbot.model.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KeyboardService {

    private final RestTemplate restTemplate;
    private final CallbackService callbackService;
    private final CircuitBreakerFactory circuitBreakerFactory;

    @Value("${keyboard.server.host}/keyboard/key")
    String getKeyboardUrl;

    public KeyboardService(RestTemplate restTemplate, @Lazy CallbackService callbackService, CircuitBreakerFactory circuitBreakerFactory) {
        this.restTemplate = restTemplate;
        this.callbackService = callbackService;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    public void handleButtonClick(Message message) {
        message.setText(sendRequest(message.getText()));
        callbackService.sendCallbackMessage(callbackService.buildCallbackMessage(message));
    }

    private String sendRequest(String value) {
        return circuitBreakerFactory.create("circuitbreaker").run(() -> restTemplate.postForEntity(getKeyboardUrl, value, String.class).getBody(), throwable -> "мне плохо");
    }
}
