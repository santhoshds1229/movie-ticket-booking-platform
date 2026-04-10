package com.xyz.moviebooking.payment.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(nullable = false, unique = true)
  private String bookingId;

  @Column(nullable = false)
  private String customerId;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PaymentStatus status;

  @Enumerated(EnumType.STRING)
  private PaymentMethod paymentMethod;

  private String transactionId;

  private String gatewayResponse;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
    if (status == null) status = PaymentStatus.PENDING;
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }

  public enum PaymentStatus {
    PENDING, SUCCESS, FAILED, REFUNDED
  }

  public enum PaymentMethod {
    CREDIT_CARD, DEBIT_CARD, UPI, NET_BANKING, WALLET
  }
}
