package com.example.callbackbot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ButtonAction {
    private String payload;
    private String type;
    private String label;
}
