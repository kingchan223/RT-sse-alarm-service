package com.example.ssealarmservice.repository;

import com.example.ssealarmservice.model.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    Optional<List<Alarm>> findAllByReceiverId(String receiverId);
}
