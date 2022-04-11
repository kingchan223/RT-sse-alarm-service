package com.example.ssealarmservice.concurrentHashMapTest;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class ConcurrentHAshMapTest {
    @Test
    void ConcurrentHAshMapTest1() {
        ConcurrentHashMap<String, SseEmitter> hashMap = new ConcurrentHashMap<>();
        hashMap.put("1", new SseEmitter(100L));
        hashMap.put("2", new SseEmitter(100L));
        assertThat(hashMap.size()).isEqualTo(2);
//        Assertions.assertThat(hashMap.size()).

        for (String s : hashMap.keySet()) {
            System.out.println(hashMap.get(s));
        }
    }
}
