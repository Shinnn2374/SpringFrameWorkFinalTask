package com.example.finalTask.serivces;

import com.example.finalTask.dto.hotel.*;
import com.example.finalTask.dto.rating.UpdateRatingRequestDto;
import org.springframework.data.domain.Pageable;

public interface HotelService {

    HotelResponseDto findById(Long id);
    HotelsListResponseDto findAll(Pageable pageable);
    HotelResponseDto create(HotelRequestDto hotelRequestDto);
    HotelResponseDto update(Long id, HotelRequestDto hotelRequestDto);
    void delete(Long id);
    HotelResponseDto updateRating(Long hotelId, UpdateRatingRequestDto updateRatingRequest);
    HotelsPageResponse findAll(HotelFilter filter, Pageable pageable);
}
