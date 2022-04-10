package com.example.ssealarmservice.service;

import com.example.ssealarmservice.model.Notification;
import com.example.ssealarmservice.repository.AlarmRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AlarmService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;//주는 시간 만큼 sse 연결이 유지되고, 시간이 지나면 자동으로 클라이언트에서 재연결 요청을 보내게 된다.
    private final AlarmRepository alarmRepository;

    public SseEmitter subscribe(String userId, String lastEventId) {
        // 1
//        String id = userId + "_" + System.currentTimeMillis();//System.currentTimeMillis()와 lastEventId가 같음
//        System.out.println("lastEventId : "+lastEventId);
        System.out.println("구독중....{"+userId+"}");
        // 2
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);

        Notification noti = createDummyNotification("dummy");
        // 3
        // 503 에러를 방지하기 위한 더미 이벤트 전송
        sendToClient(sseEmitter,noti);
        // 4
        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
//        if (!lastEventId.isEmpty()) {
//            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithId(String.valueOf(userId));
//            events.entrySet().stream() //1_123             1_223
//                    .filter(entry -> lastEventId.compareTo(entry.getKey()) > 0)//compareTo : 오른쪽이 더 크면 음수//->
//                    //entry.getKey()가 더 커야 통과.
////                    .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()))
//                    .forEach(entry -> sendToClientAndRemoveEventCache(emitter, entry.getKey(), entry.getValue(), entry.getKey()));
//        }
        alarmRepository.save(userId, sseEmitter);
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
}
