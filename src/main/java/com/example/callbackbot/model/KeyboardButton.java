package com.example.callbackbot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KeyboardButton {
   private ButtonAction action;
   private String color;
}
