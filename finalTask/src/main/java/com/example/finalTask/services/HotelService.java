package com.example.finalTask.services;

import com.example.finalTask.dto.HotelRequestDto;
import com.example.finalTask.dto.HotelResponseDto;
import com.example.finalTask.entity.Hotel;
import com.example.finalTask.exception.NotFoundException;
import com.example.finalTask.mapper.HotelMapper;
import com.example.finalTask.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public HotelResponseDto create(HotelRequestDto dto) {
        Hotel hotel = hotelMapper.toEntity(dto);
        hotel = hotelRepository.save(hotel);
        return hotelMapper.toDto(hotel);
    }

    public HotelResponseDto getById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Hotel not found with id: " + id));
        return hotelMapper.toDto(hotel);
    }

    public List<HotelResponseDto> getAll() {
        return hotelRepository.findAll().stream()
                .map(hotelMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public HotelResponseDto update(Long id, HotelRequestDto dto) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Hotel not found with id: " + id));

        hotel.setName(dto.getName());
        hotel.setAdvertisementTitle(dto.getAdvertisementTitle());
        hotel.setCity(dto.getCity());
        hotel.setAddress(dto.getAddress());
        hotel.setDistanceFromCityCenter(dto.getDistanceFromCityCenter());

        hotel = hotelRepository.save(hotel);
        return hotelMapper.toDto(hotel);
    }

    public void delete(Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new NotFoundException("Hotel not found with id: " + id);
        }
        hotelRepository.deleteById(id);
    }
}