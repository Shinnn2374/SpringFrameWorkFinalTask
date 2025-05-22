package com.example.finalTask.controller;

import com.example.finalTask.dto.room.RoomRequestDto;
import com.example.finalTask.dto.room.RoomResponseDto;
import com.example.finalTask.serivces.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/{id}")
    public RoomResponseDto findById(@PathVariable Long id) {
        return roomService.findById(id);
    }

    @GetMapping("/hotel/{hotelId}")
    public List<RoomResponseDto> findAllByHotelId(@PathVariable Long hotelId) {
        return roomService.findAllByHotelId(hotelId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoomResponseDto create(@RequestBody @Valid RoomRequestDto roomRequestDto) {
        return roomService.create(roomRequestDto);
    }

    @PutMapping("/{id}")
    public RoomResponseDto update(
            @PathVariable Long id,
            @RequestBody @Valid RoomRequestDto roomRequestDto
    ) {
        return roomService.update(id, roomRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        roomService.delete(id);
    }
}