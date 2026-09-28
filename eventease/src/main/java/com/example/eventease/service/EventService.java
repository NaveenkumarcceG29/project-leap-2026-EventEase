package com.example.eventease.service;

import com.example.eventease.entity.Event;
import com.example.eventease.exception.EventNotFoundException;
import com.example.eventease.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // Create Event
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    // Get all Events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Get Event by ID
    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() ->
                        new EventNotFoundException("Event not found with ID: " + id));
    }

    // Update Event
    public Event updateEvent(Long id, Event updatedEvent) {

        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() ->
                        new EventNotFoundException("Event not found with ID: " + id));

        existingEvent.setTitle(updatedEvent.getTitle());
        existingEvent.setDate(updatedEvent.getDate());
        existingEvent.setVenue(updatedEvent.getVenue());
        existingEvent.setMaxSeats(updatedEvent.getMaxSeats());

        return eventRepository.save(existingEvent);
    }

    // Delete Event
    public void deleteEvent(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() ->
                        new EventNotFoundException("Event not found with ID: " + id));

        eventRepository.delete(event);
    }

    // Get Upcoming Events
    public List<Event> getUpcomingEvents() {
        return eventRepository.findByDateAfter(LocalDate.now());
    }
}