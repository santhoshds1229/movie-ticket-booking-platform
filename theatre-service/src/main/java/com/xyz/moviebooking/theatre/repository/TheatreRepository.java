package com.xyz.moviebooking.theatre.repository;

import com.xyz.moviebooking.theatre.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, String> {
    List<Theatre> findByStatus(Theatre.TheatreStatus status);
    List<Theatre> findByCityAndStatus(String city, Theatre.TheatreStatus status);
    List<Theatre> findByPartnerId(String partnerId);
}
