package com.example.eventease.controller;

import com.example.eventease.entity.Registration;
import com.example.eventease.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(
            RegistrationService registrationService) {

        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<Registration> registerStudent(
            @RequestParam Long studentId,
            @RequestParam Long eventId) {

        Registration registration =
                registrationService.registerStudent(
                        studentId,
                        eventId
                );

        return new ResponseEntity<>(
                registration,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/event/{eventId}")
    public List<Registration> getEventRegistrations(
            @PathVariable Long eventId) {

        return registrationService
                .getRegistrationsForEvent(eventId);
    }

    @GetMapping("/student/{studentId}")
    public List<Registration> getStudentRegistrations(
            @PathVariable Long studentId) {

        return registrationService
                .getRegistrationsForStudent(studentId);
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<String> cancelRegistration(
            @PathVariable Long registrationId) {

        registrationService.cancelRegistration(registrationId);

        return ResponseEntity.ok(
                "Registration cancelled successfully"
        );
    }
}