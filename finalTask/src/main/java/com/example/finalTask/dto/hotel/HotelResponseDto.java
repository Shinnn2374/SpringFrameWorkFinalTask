package com.example.finalTask.dto.hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponseDto {
    private Long id;
    private String name;
    private String advertisementTitle;
    private String city;
    private String address;
    private Double distanceFromCityCenter;
    private Double rating;
    private Integer numberOfRatings;
}