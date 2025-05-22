package com.example.finalTask.serivces;

import com.example.finalTask.dto.booking.BookingRequestDto;
import com.example.finalTask.dto.booking.BookingResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    List<BookingResponseDto> findAll();
    List<BookingResponseDto> findAllByUserId(Long userId);
    BookingResponseDto create(BookingRequestDto bookingRequestDto);
    void validateBookingDates(BookingRequestDto bookingRequestDto);
    boolean isRoomBooked(Long roomId, LocalDate checkInDate, LocalDate checkOutDate);
}
