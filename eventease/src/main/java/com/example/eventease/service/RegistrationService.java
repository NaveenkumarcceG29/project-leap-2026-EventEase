package com.example.eventease.service;

import com.example.eventease.entity.Event;
import com.example.eventease.entity.Registration;
import com.example.eventease.entity.Student;
import com.example.eventease.exception.RegistrationException;
import com.example.eventease.repository.EventRepository;
import com.example.eventease.repository.RegistrationRepository;
import com.example.eventease.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final StudentRepository studentRepository;
    private final EventRepository eventRepository;

    public RegistrationService(
            RegistrationRepository registrationRepository,
            StudentRepository studentRepository,
            EventRepository eventRepository) {

        this.registrationRepository = registrationRepository;
        this.studentRepository = studentRepository;
        this.eventRepository = eventRepository;
    }

    // Register a student for an event
    public Registration registerStudent(Long studentId, Long eventId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RegistrationException("Student not found with ID: " + studentId));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new RegistrationException("Event not found with ID: " + eventId));

        // Check event date
        if (!event.getDate().isAfter(LocalDate.now())) {
            throw new RegistrationException(
                    "Registration is not allowed for past events");
        }

        // Check duplicate registration
        boolean alreadyRegistered =
                registrationRepository
                        .existsByStudentStudentIdAndEventEventId(studentId, eventId);

        if (alreadyRegistered) {
            throw new RegistrationException(
                    "Student is already registered for this event");
        }

        // Check available seats
        long registeredCount =
                registrationRepository.countByEventEventId(eventId);

        if (registeredCount >= event.getMaxSeats()) {
            throw new RegistrationException(
                    "Registration closed. Maximum seats reached");
        }

        // Create registration
        Registration registration = new Registration();

        registration.setStudent(student);
        registration.setEvent(event);
        registration.setRegistrationDate(LocalDate.now());

        return registrationRepository.save(registration);
    }

    // Get registrations for an event
    public List<Registration> getRegistrationsForEvent(Long eventId) {

        if (!eventRepository.existsById(eventId)) {
            throw new RegistrationException(
                    "Event not found with ID: " + eventId);
        }

        return registrationRepository.findByEventEventId(eventId);
    }

    // Get registrations for a student
    public List<Registration> getRegistrationsForStudent(Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new RegistrationException(
                    "Student not found with ID: " + studentId);
        }

        return registrationRepository.findByStudentStudentId(studentId);
    }

    // Cancel registration
    public void cancelRegistration(Long registrationId) {

        Registration registration =
                registrationRepository.findById(registrationId)
                        .orElseThrow(() ->
                                new RegistrationException(
                                        "Registration not found with ID: "
                                                + registrationId));

        Event event = registration.getEvent();

        // Cancellation must be before event date
        if (!event.getDate().isAfter(LocalDate.now())) {
            throw new RegistrationException(
                    "Registration cannot be cancelled on or after the event date");
        }

        registrationRepository.delete(registration);
    }
}