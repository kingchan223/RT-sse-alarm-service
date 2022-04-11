package com.example.ssealarmservice.cache;

import com.example.ssealarmservice.model.MySseEmitter;
import com.example.ssealarmservice.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@SpringBootTest
public class CacheTest {

    @Autowired
    private CacheService cacheService;

    @Test
    void getCacheTest() {
        MySseEmitter mySseEmitter1 = cacheService.getMySseEmitter("사장님강민순");

        if(!cacheService.isValidation(mySseEmitter1)){
            cacheService.updateMySseEmitter("사장님강민순", new SseEmitter(100L));
        }
        MySseEmitter mySseEmitterCached = cacheService.getMySseEmitter("사장님강민순");
        System.out.println("mySseEmitterCached = " + mySseEmitterCached);
    }
}
