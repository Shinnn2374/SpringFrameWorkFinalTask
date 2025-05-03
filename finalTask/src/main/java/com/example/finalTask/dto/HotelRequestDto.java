package com.example.finalTask.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class HotelRequestDto {
    @NotBlank
    private String name;

    @NotBlank
    private String advertisementTitle;

    @NotBlank
    private String city;

    @NotBlank
    private String address;

    @NotNull
    @Positive
    private Double distanceFromCityCenter;
}