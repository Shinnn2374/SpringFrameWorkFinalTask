package com.example.finalTask.controller;

import com.example.finalTask.dto.HotelRequestDto;
import com.example.finalTask.dto.HotelResponseDto;
import com.example.finalTask.services.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @GetMapping("/{id}")
    public HotelResponseDto getById(@PathVariable Long id) {
        return hotelService.getById(id);
    }

    @GetMapping
    public List<HotelResponseDto> getAll() {
        return hotelService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HotelResponseDto create(@RequestBody @Valid HotelRequestDto dto) {
        return hotelService.create(dto);
    }

    @PutMapping("/{id}")
    public HotelResponseDto update(
            @PathVariable Long id,
            @RequestBody @Valid HotelRequestDto dto
    ) {
        return hotelService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        hotelService.delete(id);
    }
}