package com.xyz.moviebooking.show.controller;

import com.xyz.moviebooking.show.entity.Show;
import com.xyz.moviebooking.show.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @PostMapping
    @PreAuthorize("hasRole('THEATRE_PARTNER') or hasRole('ADMIN')")
    public ResponseEntity<Show> createShow(@RequestBody Show show) {
        return ResponseEntity.status(HttpStatus.CREATED).body(showService.createShow(show));
    }

    @GetMapping
    public ResponseEntity<List<Show>> getShows(
            @RequestParam(required = false) String movieId,
            @RequestParam(required = false) String theatreId,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String language) {
        return ResponseEntity.ok(showService.getShows(movieId, theatreId, city, date, language));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Show> getShowById(@PathVariable String id) {
        return ResponseEntity.ok(showService.getShowById(id));
    }

    @GetMapping("/{id}/seats")
    public ResponseEntity<Integer> getAvailableSeats(@PathVariable String id) {
        return ResponseEntity.ok(showService.getAvailableSeats(id));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('THEATRE_PARTNER') or hasRole('ADMIN')")
    public ResponseEntity<Show> updateShowStatus(
            @PathVariable String id,
            @RequestParam Show.ShowStatus status) {
        return ResponseEntity.ok(showService.updateShowStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteShow(@PathVariable String id) {
        showService.deleteShow(id);
        return ResponseEntity.noContent().build();
    }
}
