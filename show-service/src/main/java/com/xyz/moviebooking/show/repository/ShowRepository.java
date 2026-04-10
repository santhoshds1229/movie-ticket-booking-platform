package com.xyz.moviebooking.show.repository;

import com.xyz.moviebooking.show.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, String> {
    List<Show> findByMovieId(String movieId);
    List<Show> findByTheatreId(String theatreId);
    List<Show> findByShowDate(LocalDate showDate);
    List<Show> findByMovieIdAndShowDate(String movieId, LocalDate showDate);
    List<Show> findByTheatreIdAndShowDate(String theatreId, LocalDate showDate);
    List<Show> findByMovieIdAndTheatreIdAndShowDate(String movieId, String theatreId, LocalDate showDate);
}
