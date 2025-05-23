package com.example.finalTask.dto.hotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelFilter {
    private Long id;
    private String name;
    private String city;
    private String address;
    private Double maxDistanceFromCenter;
    private Double minRating;
    private Double maxRating;
}