package com.example.finalTask.serivces.Impl;

import com.example.finalTask.dto.hotel.HotelRequestDto;
import com.example.finalTask.dto.hotel.HotelResponseDto;
import com.example.finalTask.dto.hotel.HotelsListResponseDto;
import com.example.finalTask.dto.rating.UpdateRatingRequestDto;
import com.example.finalTask.entity.Hotel;
import com.example.finalTask.exception.BadRequestException;
import com.example.finalTask.exception.NotFoundException;
import com.example.finalTask.mapper.HotelMapper;
import com.example.finalTask.repository.HotelRepository;
import com.example.finalTask.serivces.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public HotelResponseDto findById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Hotel not found with id: " + id));
        return hotelMapper.toDto(hotel);
    }

    public HotelsListResponseDto findAll(Pageable pageable) {
        Page<Hotel> hotelsPage = hotelRepository.findAll(pageable);
        List<HotelResponseDto> hotels = hotelsPage.getContent()
                .stream()
                .map(hotelMapper::toDto)
                .collect(Collectors.toList());

        return HotelsListResponseDto.builder()
                .hotels(hotels)
                .totalElements(hotelsPage.getTotalElements())
                .build();
    }

    @Transactional
    public HotelResponseDto create(HotelRequestDto hotelRequestDto) {
        Hotel hotel = hotelMapper.toEntity(hotelRequestDto);
        hotel.setRating(0.0);
        hotel.setNumberOfRatings(0);
        Hotel savedHotel = hotelRepository.save(hotel);
        return hotelMapper.toDto(savedHotel);
    }

    @Transactional
    public HotelResponseDto update(Long id, HotelRequestDto hotelRequestDto) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Hotel not found with id: " + id));
        hotelMapper.updateEntityFromDto(hotelRequestDto, hotel);
        Hotel updatedHotel = hotelRepository.save(hotel);
        return hotelMapper.toDto(updatedHotel);
    }

    @Transactional
    public void delete(Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new NotFoundException("Hotel not found with id: " + id);
        }
        hotelRepository.deleteById(id);
    }

    @Transactional
    public HotelResponseDto updateRating(Long hotelId, UpdateRatingRequestDto updateRatingRequest) {
        if (updateRatingRequest.getNewMark() < 1 || updateRatingRequest.getNewMark() > 5) {
            throw new BadRequestException("Rating must be between 1 and 5");
        }

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new NotFoundException("Hotel not found with id: " + hotelId));

        double currentRating = hotel.getRating();
        int numberOfRatings = hotel.getNumberOfRatings();

        // Вычисляем новую оценку по формуле
        double totalRating = currentRating * numberOfRatings;
        totalRating = totalRating - currentRating + updateRatingRequest.getNewMark();

        double newRating = Math.round((totalRating / numberOfRatings) * 10) / 10.0;
        numberOfRatings += 1;

        hotel.setRating(newRating);
        hotel.setNumberOfRatings(numberOfRatings);

        Hotel updatedHotel = hotelRepository.save(hotel);
        return hotelMapper.toDto(updatedHotel);
    }
}