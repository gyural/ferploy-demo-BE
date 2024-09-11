package com.FerployDemo.ferployDemo.model.messages;

import lombok.Data;

@Data
public class MessageRequest {
    private String model;
    private int max_tokens;
    private String[] messages;

    public MessageRequest(String model, int max_tokens, String content) {
        this.model = model;
        this.max_tokens = max_tokens;
        this.messages = new String[]{"{\"role\": \"user\", \"content\": \"" + content + "\"}"};
    }
}
