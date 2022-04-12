package com.example.ssealarmservice.model.entity;


import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
public class Alarm {
    @Id @GeneratedValue
    private long id;

    private String senderId;
    private String receiverId;
    private String content;

    public static Alarm create(String senderId, String receiverId, String content){
        Alarm alarm = new Alarm();
        alarm.senderId = senderId;
        alarm.receiverId = receiverId;
        alarm.content = content;
        return alarm;
    }
}
