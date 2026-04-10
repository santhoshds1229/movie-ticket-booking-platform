package com.xyz.moviebooking.booking.service;

import com.xyz.moviebooking.booking.entity.Booking;
import com.xyz.moviebooking.booking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public Booking createBooking(Booking booking) {
        booking.setBookingReference("BK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        booking.setStatus(Booking.BookingStatus.PENDING);
        Booking saved = bookingRepository.save(booking);
        kafkaTemplate.send("booking-created", saved.getId(), saved);
        return saved;
    }

    public Booking getBookingById(String id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + id));
    }

    public Booking getBookingByReference(String reference) {
        return bookingRepository.findByBookingReference(reference)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + reference));
    }

    public List<Booking> getBookingsByCustomer(String customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Transactional
    public Booking confirmBooking(String id, String paymentId) {
        Booking booking = getBookingById(id);
        booking.setPaymentId(paymentId);
        booking.setStatus(Booking.BookingStatus.CONFIRMED);
        Booking updated = bookingRepository.save(booking);
        kafkaTemplate.send("booking-confirmed", id, updated);
        return updated;
    }

    @Transactional
    public Booking cancelBooking(String id) {
        Booking booking = getBookingById(id);
        if (booking.getStatus() == Booking.BookingStatus.CONFIRMED) {
            booking.setStatus(Booking.BookingStatus.CANCELLED);
        } else if (booking.getStatus() == Booking.BookingStatus.PENDING) {
            booking.setStatus(Booking.BookingStatus.CANCELLED);
        }
        Booking updated = bookingRepository.save(booking);
        kafkaTemplate.send("booking-cancelled", id, updated);
        return updated;
    }
}
