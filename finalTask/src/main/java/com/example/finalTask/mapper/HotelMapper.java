package com.example.finalTask.mapper;



import com.example.finalTask.dto.hotel.HotelRequestDto;
import com.example.finalTask.dto.hotel.HotelResponseDto;
import com.example.finalTask.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    Hotel toEntity(HotelRequestDto hotelRequestDto);

    HotelResponseDto toDto(Hotel hotel);

    void updateEntityFromDto(HotelRequestDto hotelRequestDto, @MappingTarget Hotel hotel);
}