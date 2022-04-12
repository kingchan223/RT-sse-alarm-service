package com.example.ssealarmservice.repository;

import com.example.ssealarmservice.model.MySseEmitter;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SseEmitterRepository {
    private final SseEmitter EMPTY_SSE_EMITTER;
    private final ConcurrentHashMap<String, SseEmitter> concurrentHashMap;

    public SseEmitterRepository(){
        concurrentHashMap = new ConcurrentHashMap<>();
        EMPTY_SSE_EMITTER = new SseEmitter();
    }

    public SseEmitter save(String key, SseEmitter mySseEmitter){
        if(ObjectUtils.isEmpty(concurrentHashMap.get(key))) concurrentHashMap.put(key, mySseEmitter);
        return mySseEmitter;
    }

    public void deleteByKey(String key){
        concurrentHashMap.remove(key);
    }

    public SseEmitter getByKey(String key){
        SseEmitter sseEmitter = concurrentHashMap.get(key);
        if(ObjectUtils.isEmpty(sseEmitter)) return EMPTY_SSE_EMITTER;
        return sseEmitter;
    }
}