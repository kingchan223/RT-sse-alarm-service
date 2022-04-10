package com.example.ssealarmservice.repository;

import com.example.ssealarmservice.model.Notification;
import com.example.ssealarmservice.model.entity.Alarm;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;

public interface AlarmRepository //extends JpaRepository<Alarm, Long> {
{
    SseEmitter save(String id, SseEmitter sseEmitter);

    void deleteById(String id);

    void deleteEventCacheById(String id);

    Map<String, SseEmitter> findAllStartWithById(String id);

    void saveEventCache(String key, Notification notification);

    Map<String, Object> findAllEventCacheStartWithId(String key);

    SseEmitter getEmitterById(String key);
}
