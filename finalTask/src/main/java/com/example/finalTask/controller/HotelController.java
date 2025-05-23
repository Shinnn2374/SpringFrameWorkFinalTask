package com.example.finalTask.controller;


import com.example.finalTask.dto.hotel.HotelRequestDto;
import com.example.finalTask.dto.hotel.HotelResponseDto;
import com.example.finalTask.dto.hotel.HotelsListResponseDto;
import com.example.finalTask.dto.rating.UpdateRatingRequestDto;
import com.example.finalTask.serivces.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hotels")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @GetMapping("/{id}")
    public HotelResponseDto findById(@PathVariable Long id) {
        return hotelService.findById(id);
    }

    @GetMapping
    public HotelsListResponseDto findAll(Pageable pageable) {
        return hotelService.findAll(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HotelResponseDto create(@RequestBody @Valid HotelRequestDto hotelRequestDto) {
        return hotelService.create(hotelRequestDto);
    }

    @PutMapping("/{id}")
    public HotelResponseDto update(
            @PathVariable Long id,
            @RequestBody @Valid HotelRequestDto hotelRequestDto
    ) {
        return hotelService.update(id, hotelRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        hotelService.delete(id);
    }

    @PostMapping("/{id}/rating")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public HotelResponseDto updateRating(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRatingRequestDto updateRatingRequest
    ) {
        return hotelService.updateRating(id, updateRatingRequest);
    }
}