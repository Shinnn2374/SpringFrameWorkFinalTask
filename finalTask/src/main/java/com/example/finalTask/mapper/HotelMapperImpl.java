package com.example.finalTask.mapper;

import com.example.finalTask.dto.hotel.HotelRequestDto;
import com.example.finalTask.dto.hotel.HotelResponseDto;
import com.example.finalTask.entity.Hotel;
import org.springframework.stereotype.Component;

@Component
public class HotelMapperImpl implements HotelMapper {
    @Override
    public Hotel toEntity(HotelRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Hotel hotel = new Hotel();
        hotel.setName(dto.getName());
        hotel.setAdvertisementTitle(dto.getAdvertisementTitle());
        hotel.setCity(dto.getCity());
        hotel.setAddress(dto.getAddress());
        hotel.setDistanceFromCityCenter(dto.getDistanceFromCityCenter());
        return hotel;
    }

    @Override
    public HotelResponseDto toDto(Hotel entity) {
        if (entity == null) {
            return null;
        }

        HotelResponseDto dto = new HotelResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAdvertisementTitle(entity.getAdvertisementTitle());
        dto.setCity(entity.getCity());
        dto.setAddress(entity.getAddress());
        dto.setDistanceFromCityCenter(entity.getDistanceFromCityCenter());
        dto.setRating(entity.getRating());
        dto.setNumberOfRatings(entity.getNumberOfRatings());
        return dto;
    }
}