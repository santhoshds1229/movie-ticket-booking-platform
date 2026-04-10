package com.xyz.moviebooking.catalog.service;

import com.xyz.moviebooking.catalog.entity.Movie;
import com.xyz.moviebooking.catalog.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    @Transactional
    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Page<Movie> searchMovies(String genre, String language, String city, String status, Pageable pageable) {
        Movie.MovieStatus movieStatus = status != null ? Movie.MovieStatus.valueOf(status.toUpperCase()) : null;
        if (genre != null && language != null) {
            return movieRepository.findByGenresContainingAndLanguagesContaining(genre, language, pageable);
        } else if (genre != null) {
            return movieRepository.findByGenresContaining(genre, pageable);
        } else if (language != null) {
            return movieRepository.findByLanguagesContaining(language, pageable);
        } else if (movieStatus != null) {
            return movieRepository.findByStatus(movieStatus, pageable);
        }
        return movieRepository.findAll(pageable);
    }

    public Movie getMovieById(String id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found: " + id));
    }

    public List<Movie> getNowShowingMovies() {
        return movieRepository.findByStatus(Movie.MovieStatus.NOW_SHOWING);
    }

    public List<Movie> getComingSoonMovies() {
        return movieRepository.findByStatus(Movie.MovieStatus.COMING_SOON);
    }

    @Transactional
    public Movie updateMovie(String id, Movie updatedMovie) {
        Movie existing = getMovieById(id);
        existing.setTitle(updatedMovie.getTitle());
        existing.setDescription(updatedMovie.getDescription());
        existing.setDirector(updatedMovie.getDirector());
        existing.setCast(updatedMovie.getCast());
        existing.setDurationMinutes(updatedMovie.getDurationMinutes());
        existing.setReleaseDate(updatedMovie.getReleaseDate());
        existing.setGenres(updatedMovie.getGenres());
        existing.setLanguages(updatedMovie.getLanguages());
        existing.setStatus(updatedMovie.getStatus());
        return movieRepository.save(existing);
    }

    @Transactional
    public void deleteMovie(String id) {
        movieRepository.deleteById(id);
    }
}
