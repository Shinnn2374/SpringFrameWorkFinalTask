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
public class BookingRequestDto {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Long roomId;
    private Long userId;
}