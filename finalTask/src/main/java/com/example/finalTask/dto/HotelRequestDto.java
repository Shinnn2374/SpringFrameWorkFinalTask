package com.example.finalTask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelRequestDto {
    private String name;
    private String advertisementTitle;
    private String city;
    private String address;
    private Double distanceFromCityCenter;
}