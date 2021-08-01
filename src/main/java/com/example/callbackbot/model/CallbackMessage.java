package com.example.callbackbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Builder
@Getter
@Setter
@JsonPropertyOrder(alphabetic = true)
@EqualsAndHashCode(exclude = "randomId")
public class CallbackMessage {
    @JsonProperty(value = "user_id")
    Long userId;
    @JsonProperty(value = "random_id")
    Long randomId;
    @JsonProperty(value = "peer_id")
    Long peerId;
    String domain;
    @JsonProperty(value = "chat_id")
    Long chatId;
    @JsonProperty(value = "user_ids")
    List<Long> userIds;
    String message;
    Double lat;
    @JsonProperty(value = "long")
    Double longField;
    String attachment;
    @JsonProperty(value = "reply_to")
    Long replyTo;
    @JsonProperty(value = "forward_messages")
    String forwardMessages;
    @JsonProperty(value = "sticker_id")
    Long stickerId;
    @JsonProperty(value = "group_id")
    Long groupId;
    Keyboard keyboard;
    Long payload;
    @JsonProperty(value = "dont_parse_links")
    Integer dontParseLinks;
    @JsonProperty(value = "access_token")
    String token;
    @JsonProperty(value = "v")
    Double version;
}
