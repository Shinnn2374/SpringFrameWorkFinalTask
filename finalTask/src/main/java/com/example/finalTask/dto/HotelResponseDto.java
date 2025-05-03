package com.example.finalTask.dto;

import lombok.Data;

@Data
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