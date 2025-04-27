package com.example.finalTask.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hotels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "advertisement_title", nullable = false)
    private String advertisementTitle;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String address;

    @Column(name = "distance_from_city_center", nullable = false)
    private Double distanceFromCityCenter;

    @Column(nullable = false)
    private Double rating = 0.0;

    @Column(name = "number_of_ratings", nullable = false)
    private Integer numberOfRatings = 0;
}