package com.example.finalTask.dto.room;

import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomRequestDto {
    private String name;
    private String description;
    private String number;
    private BigDecimal price;
    private int maxPeople;
    private Long hotelId;
    private Set<String> unavailableDates;
}