package com.example.callbackbot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    private Long groupId;
    private Long clientId;
    private String text;
    private Keyboard keyboard;
}
