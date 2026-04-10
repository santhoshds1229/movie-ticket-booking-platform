package com.xyz.moviebooking.booking.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(nullable = false)
  private String customerId;

  @Column(nullable = false)
  private String showId;

  @Column(nullable = false)
  private String movieId;

  @Column(nullable = false)
  private String theatreId;

  @Column(nullable = false)
  private String screenId;

  @ElementCollection
  @CollectionTable(name = "booking_seats", joinColumns = @JoinColumn(name = "booking_id"))
  @Column(name = "seat_number")
  private java.util.List<String> seatNumbers;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal totalAmount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private BookingStatus status;

  private String paymentId;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
    if (status == null) status = BookingStatus.PENDING;
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }

  public enum BookingStatus {
    PENDING,
    CONFIRMED,
    CANCELLED,
    FAILED
  }
}
