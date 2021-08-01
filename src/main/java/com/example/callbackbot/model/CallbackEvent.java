package com.example.callbackbot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CallbackEvent {
    private EventType type;
    private Map<String, Object> object;
    @JsonProperty(value = "group_id")
    private Long groupId;
    private String secret;
    @JsonProperty(value = "event_id")
    private String eventId;

    public enum EventType {
        message_new,
        message_reply,
        confirmation
    }
}
