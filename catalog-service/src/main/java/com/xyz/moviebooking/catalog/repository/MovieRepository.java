package com.xyz.moviebooking.catalog.repository;

import com.xyz.moviebooking.catalog.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {
    List<Movie> findByStatus(Movie.MovieStatus status);
    Page<Movie> findByStatus(Movie.MovieStatus status, Pageable pageable);
    Page<Movie> findByGenresContaining(String genre, Pageable pageable);
    Page<Movie> findByLanguagesContaining(String language, Pageable pageable);
    Page<Movie> findByGenresContainingAndLanguagesContaining(String genre, String language, Pageable pageable);
}
