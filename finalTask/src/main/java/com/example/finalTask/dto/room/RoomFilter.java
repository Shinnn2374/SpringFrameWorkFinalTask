package com.example.finalTask.dto.room;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomFilter {
    private Long id;
    private String name;

    @Min(value = 1, message = "Hotel ID must be positive")
    private Long hotelId;

    @Min(value = 0, message = "Min price cannot be negative")
    private Double minPrice;

    @Min(value = 0, message = "Max price cannot be negative")
    private Double maxPrice;

    @Min(value = 1, message = "Max people must be at least 1")
    private Integer maxPeople;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkInDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkOutDate;
}