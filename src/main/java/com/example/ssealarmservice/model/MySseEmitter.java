package com.example.ssealarmservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class MySseEmitter {
    private SseEmitter sseEmitter;
    private LocalDateTime expiryDate;
}
