package com.example.callbackbot.service;

import com.example.callbackbot.model.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KeyboardService {

    private final RestTemplate restTemplate;
    private final CircuitBreakerFactory circuitBreakerFactory;

    @Value("${keyboard.server.host}/keyboard/key")
    String getKeyUrl;

    public KeyboardService(RestTemplate restTemplate, CircuitBreakerFactory circuitBreakerFactory) {
        this.restTemplate = restTemplate;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    public Message handleButtonClick(Message message) {
        return circuitBreakerFactory.create("circuitbreaker").run(() -> restTemplate.postForObject(getKeyUrl, message, Message.class));
    }
}
