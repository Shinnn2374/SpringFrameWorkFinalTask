package com.example.finalTask.mapper;



import com.example.finalTask.dto.HotelRequestDto;
import com.example.finalTask.dto.HotelResponseDto;
import com.example.finalTask.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    Hotel toEntity(HotelRequestDto hotelRequestDto);

    HotelResponseDto toDto(Hotel hotel);

    void updateEntityFromDto(HotelRequestDto hotelRequestDto, @MappingTarget Hotel hotel);
}