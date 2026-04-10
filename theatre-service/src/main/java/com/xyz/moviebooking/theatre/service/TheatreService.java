package com.xyz.moviebooking.theatre.service;

import com.xyz.moviebooking.theatre.entity.Theatre;
import com.xyz.moviebooking.theatre.repository.TheatreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TheatreService {

    private final TheatreRepository theatreRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public Theatre registerTheatre(Theatre theatre) {
        Theatre saved = theatreRepository.save(theatre);
        kafkaTemplate.send("theatre-events", saved.getId(), saved);
        return saved;
    }

    public List<Theatre> getApprovedTheatres(String city) {
        if (city != null && !city.isBlank()) {
            return theatreRepository.findByCityAndStatus(city, Theatre.TheatreStatus.APPROVED);
        }
        return theatreRepository.findByStatus(Theatre.TheatreStatus.APPROVED);
    }

    public Theatre getTheatreById(String id) {
        return theatreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Theatre not found: " + id));
    }

    public List<Theatre> getTheatresByPartner(String partnerId) {
        return theatreRepository.findByPartnerId(partnerId);
    }

    @Transactional
    public Theatre updateTheatreStatus(String id, Theatre.TheatreStatus status) {
        Theatre theatre = getTheatreById(id);
        theatre.setStatus(status);
        Theatre updated = theatreRepository.save(theatre);
        kafkaTemplate.send("theatre-status-events", id, updated);
        return updated;
    }

    @Transactional
    public Theatre updateTheatre(String id, Theatre updatedTheatre) {
        Theatre existing = getTheatreById(id);
        existing.setName(updatedTheatre.getName());
        existing.setAddress(updatedTheatre.getAddress());
        existing.setCity(updatedTheatre.getCity());
        existing.setState(updatedTheatre.getState());
        existing.setPincode(updatedTheatre.getPincode());
        existing.setPhone(updatedTheatre.getPhone());
        return theatreRepository.save(existing);
    }

    @Transactional
    public void deleteTheatre(String id) {
        theatreRepository.deleteById(id);
    }
}
