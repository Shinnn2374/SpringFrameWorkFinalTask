package com.example.finalTask.dto.hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelListResponseDto {
    private List<HotelResponseDto> hotels;
    private Long total;
}