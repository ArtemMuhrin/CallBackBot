package com.example.callbackbot.controller;

import com.example.callbackbot.model.CallbackEvent;
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
    public ResponseEntity<String> processCallback(@RequestBody CallbackEvent callbackEvent) {
        return ResponseEntity.ok(callbackService.processCallbackEvent(callbackEvent));
    }
}
