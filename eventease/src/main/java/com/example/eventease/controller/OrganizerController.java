package com.example.eventease.controller;

import com.example.eventease.entity.Organizer;
import com.example.eventease.service.OrganizerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organizers")
public class OrganizerController {

    private final OrganizerService organizerService;

    public OrganizerController(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }

    @PostMapping
    public ResponseEntity<Organizer> createOrganizer(
            @Valid @RequestBody Organizer organizer) {

        return new ResponseEntity<>(
                organizerService.createOrganizer(organizer),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public List<Organizer> getAllOrganizers() {
        return organizerService.getAllOrganizers();
    }

    @GetMapping("/{id}")
    public Organizer getOrganizerById(@PathVariable Long id) {
        return organizerService.getOrganizerById(id);
    }

    @PutMapping("/{id}")
    public Organizer updateOrganizer(
            @PathVariable Long id,
            @Valid @RequestBody Organizer organizer) {

        return organizerService.updateOrganizer(id, organizer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrganizer(
            @PathVariable Long id) {

        organizerService.deleteOrganizer(id);

        return ResponseEntity.ok("Organizer deleted successfully");
    }
}