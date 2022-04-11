package com.example.ssealarmservice.repository;

import com.example.ssealarmservice.model.Notification;
import com.example.ssealarmservice.model.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    Optional<List<Alarm>> findAllByReceiverId();

}
