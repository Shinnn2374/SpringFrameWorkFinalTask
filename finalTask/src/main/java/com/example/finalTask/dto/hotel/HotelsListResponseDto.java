package com.example.finalTask.dto.hotel;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelsListResponseDto {
    private List<HotelResponseDto> hotels;
    private Long totalElements;
}