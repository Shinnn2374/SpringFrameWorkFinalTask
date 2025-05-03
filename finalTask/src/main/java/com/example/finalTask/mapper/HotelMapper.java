package com.example.finalTask.mapper;

import com.example.finalTask.dto.HotelRequestDto;
import com.example.finalTask.dto.HotelResponseDto;
import com.example.finalTask.model.Hotel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", constant = "0.0")
    @Mapping(target = "numberOfRatings", constant = "0")
    Hotel toEntity(HotelRequestDto dto);

    HotelResponseDto toDto(Hotel entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "numberOfRatings", ignore = true)
    void updateEntityFromDto(HotelRequestDto dto, @MappingTarget Hotel entity);
}