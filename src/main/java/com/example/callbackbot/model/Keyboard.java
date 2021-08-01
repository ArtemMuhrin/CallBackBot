package com.example.callbackbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Keyboard {
    private ArrayList<ArrayList<KeyboardButton>> buttons;
    @JsonProperty("one_time")
    private Boolean oneTime;
}
