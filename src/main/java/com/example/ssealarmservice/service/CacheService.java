package com.example.ssealarmservice.service;

import com.example.ssealarmservice.model.MySseEmitter;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;

@Slf4j
@Service
public class CacheService {

    private static final MySseEmitter EMPTY_MY_SSEEMITTER = new MySseEmitter();

    @Cacheable(cacheNames = "mySseEmitterStore", key = "#key")
    public MySseEmitter getMySseEmitter(final String key) {
        log.info("해당 key : {} 에 알맞는 캐싱 데이터가 없습니다.", key);
        return EMPTY_MY_SSEEMITTER;
    }

    @CachePut(cacheNames = "mySseEmitterStore", key = "#key")
    public MySseEmitter updateMySseEmitter(final String key, final SseEmitter sseEmitter) {
        log.info("해당 key : {} 에 대한 데이터가 업데이트 되었습니다.", key);
        MySseEmitter mySseEmitter = new MySseEmitter();
        mySseEmitter.setSseEmitter(sseEmitter);
        mySseEmitter.setExpiryDate(LocalDateTime.now().plusMinutes(30));
        return mySseEmitter;
    }

    @CacheEvict(cacheNames = "mySseEmitterStore", key = "#key")
    public boolean expireMySseEmitter(final String key) {
        log.info("해당 key : {} 에 대한 캐시 데이터를 삭제합니다.");
        return true;
    }

    public boolean isValidation(final MySseEmitter mySseEmitter) {
        return (!ObjectUtils.isEmpty(mySseEmitter)) &&
                (!ObjectUtils.isEmpty(mySseEmitter.getSseEmitter())) &&
                (!ObjectUtils.isEmpty(mySseEmitter.getExpiryDate())) &&
                (mySseEmitter.getExpiryDate().isAfter(LocalDateTime.now()));

    }
}
