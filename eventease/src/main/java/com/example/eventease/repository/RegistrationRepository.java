package com.example.eventease.repository;

import com.example.eventease.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    long countByEventEventId(Long eventId);

    boolean existsByStudentStudentIdAndEventEventId(Long studentId, Long eventId);

    List<Registration> findByEventEventId(Long eventId);

    List<Registration> findByStudentStudentId(Long studentId);
}