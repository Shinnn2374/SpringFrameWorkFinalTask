package com.example.finalTask.services;



import com.example.finalTask.dto.HotelRequestDto;
import com.example.finalTask.dto.HotelResponseDto;
import com.example.finalTask.utils.exceptions.NotFoundException;
import com.example.finalTask.mapper.HotelMapper;
import com.example.finalTask.model.Hotel;
import com.example.finalTask.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Transactional
    public HotelResponseDto create(HotelRequestDto dto) {
        Hotel hotel = hotelMapper.toEntity(dto);
        return hotelMapper.toDto(hotelRepository.save(hotel));
    }

    @Transactional(readOnly = true)
    public HotelResponseDto getById(Long id) {
        return hotelMapper.toDto(getHotelById(id));
    }

    @Transactional(readOnly = true)
    public List<HotelResponseDto> getAll() {
        return hotelRepository.findAll().stream()
                .map(hotelMapper::toDto)
                .toList();
    }

    @Transactional
    public HotelResponseDto update(Long id, HotelRequestDto dto) {
        Hotel hotel = getHotelById(id);
        hotelMapper.updateEntityFromDto(dto, hotel);
        return hotelMapper.toDto(hotelRepository.save(hotel));
    }

    @Transactional
    public void delete(Long id) {
        hotelRepository.deleteById(id);
    }

    private Hotel getHotelById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Hotel not found with id: " + id));
    }
}