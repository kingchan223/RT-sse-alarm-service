package com.example.ssealarmservice.service;

import com.example.ssealarmservice.model.Notification;
import com.example.ssealarmservice.model.entity.Alarm;
import com.example.ssealarmservice.model.network.Header;
import com.example.ssealarmservice.model.network.response.AlarmResponse;
import com.example.ssealarmservice.repository.AlarmRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AlarmService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;//주는 시간 만큼 sse 연결이 유지되고, 시간이 지나면 자동으로 클라이언트에서 재연결 요청을 보내게 된다.
    private static final Notification dummyNoti = new Notification("dummy");
    private final AlarmRepository alarmRepository;
    private final CacheService cacheService;

    public SseEmitter subscribe(String nickname) {
        System.out.println("구독중....{"+nickname+"}");
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
        sendToClient(sseEmitter,dummyNoti);// 503 에러를 방지하기 위한 더미 이벤트 전송
        cacheService.updateMySseEmitter(nickname,sseEmitter);
        return sseEmitter;
    }


//    public void send(Notification notification) {
////        String receiverId = notification.getReceiverId();
//
//        // 로그인 한 유저의 SseEmitter 모두 가져오기
////        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllStartWithById(String.valueOf(receiverId));
////        System.out.println("sseEmitters.size():"+sseEmitters.size());
////        sseEmitters.forEach(
////                (key, emitter) -> {
////                    System.out.println("@@@@@@@@ key = " + key);
////                    // 데이터 캐시 저장(유실된 데이터 처리하기 위함)
////                    emitterRepository.saveEventCache(key, notification);
////                    // 데이터 전송
////                    sendToClient(emitter, String.valueOf(notification.getReceiver().getReceiverId()), notification);
////                }
////        );
//        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
//        sendToClient(sseEmitter, notification);
////        alarmRepository.save(); //레디스에 저장하기
//    }

//    private Notification createNotification(Receiver receiver, Review review) {
//        return Notification.builder()
//                .receiver(receiver)
//                .sender(review.getSenderId())
//                .content(review.getContent())
//                .build();
//    }

    private void sendToClient(SseEmitter emitter,Notification notification){
        try {
            emitter.send(SseEmitter.event()
                    .id(notification.getReceiverId())
                    .name("sse")
                    .data(notification.getContent()));
        } catch (IOException exception) {
//            emitterRepository.deleteById(id);
            throw new RuntimeException("연결 오류!");
        }
    }

    /* 서버가 클라이언트에게 메시지 보내기 */
    public void send(Notification notification){
//        String id = "1";
        //emitterRepository.saveEventCache(id, createNotification(content));
        System.out.println("SEND....{"+notification+"}");
        SseEmitter emitter = alarmRepository.getEmitterById(notification.getReceiverId());
//        emitterRepository.save(id, sseEmitter);
        // 로그인 한 유저의 SseEmitter 모두 가져오기
//        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllStartWithById(id);
//        System.out.println(sseEmitters);
//        sseEmitters.forEach(
//                (key, emitter) -> {
//                    // 데이터 캐시 저장(유실된 데이터 처리하기 위함)
//                    emitterRepository.saveEventCache(key, notification);
//                    System.out.println("key"+key);
//                    // 데이터 전송
//                    sendToClient(emitter, key, content);
//                }
//        );
        sendToClient(emitter, notification);
//        emitterRepository.saveEventCache(id, createNotification(content));//TODO DB에 notification 저장
    }

    private Notification createDummyNotification(String content) {
        return new Notification(content);
    }


    public Header<List<AlarmResponse>> getAllAlarms() {
        List<Alarm> alarms = alarmRepository.findAllByReceiverId().orElse(new ArrayList<>());
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

//    private void sendToClientAndRemoveEventCache(SseEmitter emitter, String id, Object data, String key){
//        try {
//            emitter.send(SseEmitter.event()
//                    .id(id)
//                    .name("sse")
//                    .data(data));
//
//        } catch (IOException exception) {
//            emitterRepository.deleteById(id);
//            throw new RuntimeException("연결 오류!");
//        }finally{
//            emitterRepository.deleteEventCacheById(key);
//        }
//    }
