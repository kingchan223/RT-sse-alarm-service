package com.example.ssealarmservice.model.network.response;

import com.example.ssealarmservice.model.entity.Alarm;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AlarmResponse {
    private long id;
    private String senderId;
    private String receiverId;
    private String content;

    public static AlarmResponse create(Alarm a) {
        AlarmResponse alarmResponse = new AlarmResponse();
        alarmResponse.id = a.getId();
        alarmResponse.receiverId = a.getReceiverId();
        alarmResponse.senderId = a.getSenderId();
        alarmResponse.content = a.getContent();
        return alarmResponse;
    }
}
