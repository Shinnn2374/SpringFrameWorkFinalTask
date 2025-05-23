package com.example.finalTask.serivces;

import com.example.finalTask.dto.room.RoomFilter;
import com.example.finalTask.dto.room.RoomRequestDto;
import com.example.finalTask.dto.room.RoomResponseDto;
import com.example.finalTask.dto.room.RoomsPageResponse;

import java.awt.print.Pageable;
import java.util.List;

public interface RoomService {
    RoomResponseDto findById(Long id);
    List<RoomResponseDto> findAllByHotelId(Long hotelId);
    RoomResponseDto create(RoomRequestDto roomRequestDto);
    RoomResponseDto update(Long id, RoomRequestDto roomRequestDto);
    void delete(Long id);
    RoomsPageResponse findAll(RoomFilter filter, Pageable pageable);
}
