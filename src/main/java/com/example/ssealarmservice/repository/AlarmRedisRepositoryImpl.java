package com.example.ssealarmservice.repository;

import com.example.ssealarmservice.model.Notification;
import com.example.ssealarmservice.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

@Primary
@RequiredArgsConstructor
@Repository
public class AlarmRedisRepositoryImpl implements AlarmRepository{

    private final RedisService redisService;
    private final ObjectMapper objectMapper;

    @Override
    public SseEmitter save(String id, SseEmitter sseEmitter) {
        redisService.setEmitter(id, sseEmitter);
        return sseEmitter;
    }

    @Override
    public SseEmitter getEmitterById(String key) {
        return redisService.getEmitterById(key);
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public void deleteEventCacheById(String id) {

    }

    @Override
    public Map<String, SseEmitter> findAllStartWithById(String id) {
        return null;
    }

    @Override
    public void saveEventCache(String key, Notification notification) {

    }

    @Override
    public Map<String, Object> findAllEventCacheStartWithId(String key) {
        return null;
    }

}
