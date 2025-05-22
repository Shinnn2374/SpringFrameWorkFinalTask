package com.example.finalTask.controller;

import com.example.finalTask.dto.booking.BookingRequestDto;
import com.example.finalTask.dto.booking.BookingResponseDto;
import com.example.finalTask.serivces.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping
    public List<BookingResponseDto> findAll() {
        return bookingService.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<BookingResponseDto> findAllByUserId(@PathVariable Long userId) {
        return bookingService.findAllByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponseDto create(@RequestBody @Valid BookingRequestDto bookingRequestDto) {
        return bookingService.create(bookingRequestDto);
    }
}