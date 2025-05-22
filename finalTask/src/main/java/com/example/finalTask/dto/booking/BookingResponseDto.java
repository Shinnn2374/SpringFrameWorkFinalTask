package com.example.finalTask.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDto {
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Long roomId;
    private String roomName;
    private Long userId;
    private String username;
}