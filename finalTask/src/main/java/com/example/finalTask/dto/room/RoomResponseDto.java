package com.example.finalTask.dto.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponseDto {
    private Long id;
    private String name;
    private String description;
    private Integer number;
    private Double price;
    private Integer maxPeople;
    private List<LocalDate> unavailableDates;
    private Long hotelId;
    private String hotelName;
}