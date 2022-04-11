package com.example.ssealarmservice.controller;

import com.example.ssealarmservice.model.Notification;
import com.example.ssealarmservice.model.network.Header;
import com.example.ssealarmservice.model.network.response.AlarmResponse;
import com.example.ssealarmservice.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.ws.rs.DELETE;
import java.util.List;

// TODO 메시지, dingsta 좋아요, secondTrade 관심있어요,
@RequiredArgsConstructor
@RestController
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping(value = "/sub/{userId}", produces = "text/event-stream")//실제 적용시에는 access-token을 활용할 계획이다
    public SseEmitter subscribe(@PathVariable String nickname
//                                @RequestHeader(
//                                        value = "Last-Event-ID",//클라이언트가 마지막으로 수신한 데이터의 id값
//                                        required = false,
//                                        defaultValue = "") String lastEventId
    ) {
        return alarmService.subscribe(nickname);
    }

    @PostMapping("/alarm/message")
    public String sendMessage(@RequestBody Notification notification) {
        alarmService.send(notification);
        return "전송완료";
    }

    @GetMapping("/alarm/dingsta_like")
    public String dingstaLike() {
        return "전송이 완료되었습니다.";
    }

    @GetMapping("/alarm/zzim")
    public String zzim() {
        return "전송이 완료되었습니다.";
    }

    @GetMapping("/alarm")
    public Header<List<AlarmResponse>> getAllAlarms() {
        return alarmService.getAllAlarms();
    }

    @DeleteMapping("/alarm")
    public Header<Void> deleteById(@PathVariable Long id){
        return alarmService.deleteById(id);
    }


}
