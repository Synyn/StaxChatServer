package com.staxchat.dto;

import com.fasterxml.jackson.databind.JsonNode;

public class Message {
    private MessageType messageType;
    private JsonNode body;

    public JsonNode getBody() {
        return body;
    }

    public void setBody(JsonNode body) {
        this.body = body;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
