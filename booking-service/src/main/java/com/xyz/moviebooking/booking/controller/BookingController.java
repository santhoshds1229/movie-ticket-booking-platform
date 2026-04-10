package com.xyz.moviebooking.booking.controller;

import com.xyz.moviebooking.booking.dto.BookingRequest;
import com.xyz.moviebooking.booking.model.Booking;
import com.xyz.moviebooking.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

  private final BookingService bookingService;

  @PostMapping
  public ResponseEntity<Booking> createBooking(
      @RequestHeader("X-Customer-Id") String customerId,
      @Valid @RequestBody BookingRequest request) {
    Booking booking = bookingService.createBooking(customerId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(booking);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Booking> getBookingById(
      @PathVariable String id,
      @RequestHeader("X-Customer-Id") String customerId) {
    return ResponseEntity.ok(bookingService.getBookingById(id));
  }

  @GetMapping("/customer")
  public ResponseEntity<List<Booking>> getCustomerBookings(
      @RequestHeader("X-Customer-Id") String customerId) {
    return ResponseEntity.ok(bookingService.getBookingsByCustomerId(customerId));
  }

  @PostMapping("/{id}/confirm")
  public ResponseEntity<Booking> confirmBooking(
      @PathVariable String id,
      @RequestParam String paymentId) {
    return ResponseEntity.ok(bookingService.confirmBooking(id, paymentId));
  }

  @PostMapping("/{id}/cancel")
  public ResponseEntity<Booking> cancelBooking(
      @PathVariable String id,
      @RequestHeader("X-Customer-Id") String customerId) {
    return ResponseEntity.ok(bookingService.cancelBooking(id));
  }
}
