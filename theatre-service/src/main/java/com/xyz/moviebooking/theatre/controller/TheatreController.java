package com.xyz.moviebooking.theatre.controller;

import com.xyz.moviebooking.theatre.entity.Theatre;
import com.xyz.moviebooking.theatre.service.TheatreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/theatres")
@RequiredArgsConstructor
public class TheatreController {

    private final TheatreService theatreService;

    @PostMapping
    @PreAuthorize("hasRole('THEATRE_PARTNER')")
    public ResponseEntity<Theatre> registerTheatre(@Valid @RequestBody Theatre theatre) {
        return ResponseEntity.status(HttpStatus.CREATED).body(theatreService.registerTheatre(theatre));
    }

    @GetMapping
    public ResponseEntity<List<Theatre>> getAllApprovedTheatres(
            @RequestParam(required = false) String city) {
        return ResponseEntity.ok(theatreService.getApprovedTheatres(city));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Theatre> getTheatreById(@PathVariable String id) {
        return ResponseEntity.ok(theatreService.getTheatreById(id));
    }

    @GetMapping("/partner/{partnerId}")
    @PreAuthorize("hasRole('THEATRE_PARTNER') or hasRole('ADMIN')")
    public ResponseEntity<List<Theatre>> getTheatresByPartner(@PathVariable String partnerId) {
        return ResponseEntity.ok(theatreService.getTheatresByPartner(partnerId));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Theatre> updateTheatreStatus(
            @PathVariable String id,
            @RequestParam Theatre.TheatreStatus status) {
        return ResponseEntity.ok(theatreService.updateTheatreStatus(id, status));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('THEATRE_PARTNER')")
    public ResponseEntity<Theatre> updateTheatre(
            @PathVariable String id,
            @Valid @RequestBody Theatre theatre) {
        return ResponseEntity.ok(theatreService.updateTheatre(id, theatre));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTheatre(@PathVariable String id) {
        theatreService.deleteTheatre(id);
        return ResponseEntity.noContent().build();
    }
}
