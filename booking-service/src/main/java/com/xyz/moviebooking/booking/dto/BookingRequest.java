package com.xyz.moviebooking.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BookingRequest {

  @NotBlank(message = "Show ID is required")
  private String showId;

  @NotBlank(message = "Movie ID is required")
  private String movieId;

  @NotBlank(message = "Theatre ID is required")
  private String theatreId;

  @NotBlank(message = "Screen ID is required")
  private String screenId;

  @NotEmpty(message = "At least one seat must be selected")
  private List<String> seatNumbers;

  @NotNull(message = "Total amount is required")
  @Positive(message = "Total amount must be positive")
  private BigDecimal totalAmount;
}
