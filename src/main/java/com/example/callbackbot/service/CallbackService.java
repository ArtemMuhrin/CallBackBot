package com.example.callbackbot.service;

import com.example.callbackbot.util.CallbackEventProcessor;
import com.example.callbackbot.util.CallbackEventProcessorFactory;
import com.example.callbackbot.dto.CallbackEventDto;
import com.example.callbackbot.dto.CallbackMessageSendDto;
import com.example.callbackbot.dto.CallbackResponseDto;
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
    private final CallbackEventProcessorFactory callbackEventProcessorFactory;

    @Value("https://api.vk.com/method/messages.send")
    private String sendUrl;

    public CallbackService(ObjectMapper objectMapper, RestTemplate restTemplate, CallbackEventProcessorFactory callbackEventProcessorFactory) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
        this.callbackEventProcessorFactory = callbackEventProcessorFactory;
    }

    public String processCallbackEvent(CallbackEventDto callbackEventDto) {
        CallbackEventProcessor callbackEventProcessor = callbackEventProcessorFactory.getEventProcessor(callbackEventDto.getType());
        return callbackEventProcessor.process(callbackEventDto);
    }

    public void sendCallbackMessage(CallbackMessageSendDto messageDto) {
        URI uri = createUri(messageDto);
        ResponseEntity<CallbackResponseDto> responseEntity = restTemplate.postForEntity(uri, null, CallbackResponseDto.class);

        if (responseEntity.getBody().getError() != null) {
            logger.warn(responseEntity.getBody().getError().getErrorMsg());
        }
    }

    private URI createUri(CallbackMessageSendDto messageDto) {
        MultiValueMap<String, String> map = objectMapper.convertValue(messageDto, LinkedMultiValueMap.class);
        return UriComponentsBuilder
                .fromHttpUrl(sendUrl)
                .queryParams(map)
                .build()
                .toUri();
    }
}
