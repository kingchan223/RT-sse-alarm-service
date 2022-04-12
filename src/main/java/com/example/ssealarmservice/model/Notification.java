package com.example.ssealarmservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Notification {
    private String senderId;
    private String receiverId;
    private String content;
    public Notification() {}

    public Notification(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
