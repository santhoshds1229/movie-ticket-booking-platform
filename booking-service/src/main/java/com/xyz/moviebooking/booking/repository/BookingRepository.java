package com.xyz.moviebooking.booking.repository;

import com.xyz.moviebooking.booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

  List<Booking> findByCustomerId(String customerId);

  List<Booking> findByShowId(String showId);

  List<Booking> findByTheatreId(String theatreId);

  List<Booking> findByCustomerIdAndStatus(String customerId, Booking.BookingStatus status);

  @Query("SELECT b FROM Booking b WHERE b.showId = :showId AND b.status <> 'CANCELLED'")
  List<Booking> findActiveBookingsByShowId(String showId);

  Optional<Booking> findByIdAndCustomerId(String id, String customerId);
}
