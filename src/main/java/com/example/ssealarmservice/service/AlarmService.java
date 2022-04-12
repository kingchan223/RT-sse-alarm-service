package com.example.ssealarmservice.service;

import com.example.ssealarmservice.model.MySseEmitter;
import com.example.ssealarmservice.model.Notification;
import com.example.ssealarmservice.model.entity.Alarm;
import com.example.ssealarmservice.model.network.Header;
import com.example.ssealarmservice.model.network.response.AlarmResponse;
import com.example.ssealarmservice.repository.AlarmRepository;
import com.example.ssealarmservice.repository.SseEmitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AlarmService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;//주는 시간 만큼 sse 연결이 유지되고, 시간이 지나면 자동으로 클라이언트에서 재연결 요청을 보내게 된다.
    private static final Notification dummyNoti = new Notification("dummy");
    private final AlarmRepository alarmRepository;
//    private final CacheService cacheService;
    private final SseEmitterRepository sseEmitterRepository;

    @Transactional
    public void sendMessage(String senderNickname, String receiverNickname){
        SseEmitter sseEmitter = sseEmitterRepository.getByKey(receiverNickname);
        String content = senderNickname + "님이 메세지를 보냈습니다.";
        Notification notification = new Notification(senderNickname, receiverNickname, content);
        sendToClient(sseEmitter, notification);
        alarmRepository.save(Alarm.create(senderNickname, receiverNickname, content));
    }

    @Transactional
    public void dingstaLike(String senderNickname, String receiverNickname){
        SseEmitter sseEmitter = sseEmitterRepository.getByKey(receiverNickname);
        String content = senderNickname + "님이 게시물을 좋아합니다.";
        Notification notification = new Notification(senderNickname, receiverNickname, content);
        sendToClient(sseEmitter, notification);
        alarmRepository.save(Alarm.create(senderNickname, receiverNickname, content));
    }

    @Transactional
    public void zzim(String senderNickname, String receiverNickname){
        SseEmitter sseEmitter = sseEmitterRepository.getByKey(receiverNickname);
        String content = senderNickname + "님이 중고거래 물품을 찜했습니다..";
        Notification notification = new Notification(senderNickname, receiverNickname,content);
        sendToClient(sseEmitter, notification);
        alarmRepository.save(Alarm.create(senderNickname, receiverNickname, content));
    }

    public SseEmitter subscribe(String nickname){
        System.out.println("구독중....{"+nickname+"}");
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
        sendToClient(sseEmitter,dummyNoti);// 503 에러를 방지하기 위한 더미 이벤트 전송
        sseEmitterRepository.save(nickname, sseEmitter);
        return sseEmitter;
    }

    private void sendToClient(SseEmitter emitter, Notification notification){
        try{
            emitter.send(SseEmitter.event()
                    .id(notification.getReceiverId())
                    .name("sse")
                    .data(notification.getContent()));
        }catch (IOException exception){
            throw new RuntimeException("연결 오류!");
        }
    }

    public Header<List<AlarmResponse>> getAllAlarms(String senderId) {
        List<Alarm> alarms = alarmRepository.findAllByReceiverId(senderId).orElse(new ArrayList<>());
        ArrayList<AlarmResponse> alarmResponses = new ArrayList<>();
        alarms.forEach(a -> alarmResponses.add(AlarmResponse.create(a)));
        return Header.OK(alarmResponses);
    }

    @Transactional
    public Header<Void> deleteById(Long id) {
        alarmRepository.deleteById(id);
        return Header.OK();
    }
}
