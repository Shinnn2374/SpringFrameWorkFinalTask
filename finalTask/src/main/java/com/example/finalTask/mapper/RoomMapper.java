package com.example.finalTask.mapper;

import com.example.finalTask.dto.room.RoomRequestDto;
import com.example.finalTask.dto.room.RoomResponseDto;
import com.example.finalTask.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    @Mapping(target = "hotel", ignore = true)
    Room toEntity(RoomRequestDto roomRequestDto);

    @Mapping(source = "hotel.id", target = "hotelId")
    @Mapping(source = "hotel.name", target = "hotelName")
    RoomResponseDto toDto(Room room);

    @Mapping(target = "hotel", ignore = true)
    void updateEntityFromDto(RoomRequestDto roomRequestDto, @MappingTarget Room room);
}