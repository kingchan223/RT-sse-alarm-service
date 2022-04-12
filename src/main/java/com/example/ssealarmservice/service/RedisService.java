package com.example.ssealarmservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Objects;

//@RequiredArgsConstructor
//@Service
public class RedisService {
//
//    private final RedisTemplate redisTemplate;
//    private final Environment env;
//    private final Long emmiter_duration_day = 7L;
//    private final Long event_redis_duration_day = 14L;
//
//    public void setEmitter(String key, SseEmitter emitter){
//        ValueOperations<String, SseEmitter> values = redisTemplate.opsForValue();
//        values.set(key, emitter, Duration.ofDays(emmiter_duration_day));
//    }
//
//    public void setEvent(String key, String event){
//        ValueOperations<String, String> values = redisTemplate.opsForValue();
//        values.set(key, event, Duration.ofDays(event_redis_duration_day));
//    }
//
////    public String setAdminAccessJwtToken(String email, String nickname, String role){
////        String accessToken = tokenUtils.createAdminAccessJwtToken(email, nickname, role);
////        ValueOperations<String, String> values = redisTemplate.opsForValue();
////        values.set(email+env.getProperty("jwt.redis_rt"), accessToken, Duration.ofMinutes(20));
////        return accessToken;
////    }
//
////    public String setRefreshJwtToken(String email, String nickname, String role){
////        String refreshToken = tokenUtils.createRefreshJwtToken(email, nickname, role);
////        ValueOperations<String, String> values = redisTemplate.opsForValue();
////        values.set(email+env.getProperty("jwt.redis_rt"), refreshToken, Duration.ofDays(refresh_redis_duration_day));
////        return refreshToken;
////    }
//
////    public String setAdminRefreshJwtToken(String email, String nickname, String role){
////        String refreshToken = tokenUtils.createAdminRefreshJwtToken(email, nickname, role);
////        ValueOperations<String, String> values = redisTemplate.opsForValue();
////        values.set(email+env.getProperty("jwt.redis_rt"), refreshToken, Duration.ofDays(14));
////        return refreshToken;
////    }
//
//
//    private String getJwtToken(String email){
//        ValueOperations<String, String> values = redisTemplate.opsForValue();
//        return values.get(email);
//    }
//
//    private void deleteEmitterByKey(String key){
//        redisTemplate.delete(key);
//    }
//
//    private void deleteEventByKey(String key){
//        redisTemplate.delete(key);
//    }
//
//    public SseEmitter getEmitterById(String key) {
//        ValueOperations<String, SseEmitter> values = redisTemplate.opsForValue();
//        return values.get(key);
//    }
}
