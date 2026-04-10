package com.xyz.moviebooking.theatre.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "screens")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int totalSeats;

    @Enumerated(EnumType.STRING)
    private ScreenType screenType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theatre_id", nullable = false)
    private Theatre theatre;

    public enum ScreenType {
        STANDARD, IMAX, DOLBY, SCREEN_X, DRIVE_IN
    }
}
