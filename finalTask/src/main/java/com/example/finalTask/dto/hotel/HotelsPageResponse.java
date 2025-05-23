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
public class HotelsPageResponse {
    private List<HotelResponseDto> hotels;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;
}