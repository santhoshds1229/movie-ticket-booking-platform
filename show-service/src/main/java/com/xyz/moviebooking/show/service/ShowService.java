package com.xyz.moviebooking.show.service;

import com.xyz.moviebooking.show.entity.Show;
import com.xyz.moviebooking.show.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowService {

    private final ShowRepository showRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public Show createShow(Show show) {
        Show saved = showRepository.save(show);
        kafkaTemplate.send("show-events", saved.getId(), saved);
        return saved;
    }

    public List<Show> getShows(String movieId, String theatreId, String city, LocalDate date, String language) {
        if (movieId != null && date != null) {
            return showRepository.findByMovieIdAndShowDate(movieId, date);
        } else if (theatreId != null && date != null) {
            return showRepository.findByTheatreIdAndShowDate(theatreId, date);
        } else if (movieId != null) {
            return showRepository.findByMovieId(movieId);
        } else if (theatreId != null) {
            return showRepository.findByTheatreId(theatreId);
        } else if (date != null) {
            return showRepository.findByShowDate(date);
        }
        return showRepository.findAll();
    }

    public Show getShowById(String id) {
        return showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Show not found: " + id));
    }

    public int getAvailableSeats(String id) {
        return getShowById(id).getAvailableSeats();
    }

    @Transactional
    public Show updateShowStatus(String id, Show.ShowStatus status) {
        Show show = getShowById(id);
        show.setStatus(status);
        return showRepository.save(show);
    }

    @Transactional
    public void deleteShow(String id) {
        showRepository.deleteById(id);
    }
}
