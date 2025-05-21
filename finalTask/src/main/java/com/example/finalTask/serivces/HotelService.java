package com.example.finalTask.serivces;

import com.example.finalTask.dto.hotel.HotelRequestDto;
import com.example.finalTask.dto.hotel.HotelResponseDto;
import com.example.finalTask.dto.hotel.HotelsListResponseDto;
import org.springframework.data.domain.Pageable;

public interface HotelService {

    HotelResponseDto findById(Long id);
    HotelsListResponseDto findAll(Pageable pageable);
    HotelResponseDto create(HotelRequestDto hotelRequestDto);
    HotelResponseDto update(Long id, HotelRequestDto hotelRequestDto);
    void delete(Long id);
}
