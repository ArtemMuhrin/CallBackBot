package com.example.callbackbot.controller;

import com.example.callbackbot.dto.CallbackEventDto;
import com.example.callbackbot.service.CallbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallbackController {

    private final CallbackService callbackService;

    public CallbackController(CallbackService callbackService) {
        this.callbackService = callbackService;
    }

    @PostMapping()
    @ResponseBody
    public ResponseEntity<String> processCallback(@RequestBody CallbackEventDto callbackEventDto) {
        return ResponseEntity.ok(callbackService.processCallbackEvent(callbackEventDto));
    }
}
