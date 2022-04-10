package com.example.ssealarmservice.repository;

import com.example.ssealarmservice.model.Notification;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


//@Primary
@Repository
public class AlarmRepositoryImpl implements AlarmRepository{

    private final ConcurrentHashMap<String, SseEmitter> sseEmitterContainer;
    private final ConcurrentHashMap<String, Notification> eventContainer;

    public AlarmRepositoryImpl(){
        sseEmitterContainer = new ConcurrentHashMap<>();
        eventContainer = new ConcurrentHashMap<>();
    }

    @Override
    public SseEmitter save(String id, SseEmitter sseEmitter){
        System.out.println("save");
        sseEmitterContainer.put(id, sseEmitter);
        return sseEmitter;
    }

    @Override
    public SseEmitter getEmitterById(String id) {
        return sseEmitterContainer.get(id);
    }

    @Override
    public void deleteById(String id){
        System.out.println("deleteById");
        for (String k : sseEmitterContainer.keySet())
            if((k.split("_")[0]).equals(id))
                sseEmitterContainer.remove(k);
    }

    @Override
    public void deleteEventCacheById(String id) {
        System.out.println("deleteById");
        eventContainer.remove(id);
    }

    @Override
    public Map<String, SseEmitter> findAllStartWithById(String id) {
        System.out.println("findAllStartWithById");
        HashMap<String, SseEmitter> hashMap = new HashMap<>();
        for (String k : sseEmitterContainer.keySet())
            if((k.split("_")[0]).equals(id))
                hashMap.put(k, sseEmitterContainer.get(k));
        return hashMap;
    }

    @Override
    public void saveEventCache(String key, Notification notification) {
        System.out.println("saveEventCache");
        eventContainer.put(key, notification);
    }

    @Override
    public Map<String, Object> findAllEventCacheStartWithId(String key) {
        System.out.println("findAllEventCacheStartWithId");
        HashMap<String, Object> hashMap = new HashMap<>();
        for (String k : eventContainer.keySet())
            if((k.split("_")[0]).equals(key))
                hashMap.put(k, eventContainer.get(k));
        return hashMap;
    }


}
