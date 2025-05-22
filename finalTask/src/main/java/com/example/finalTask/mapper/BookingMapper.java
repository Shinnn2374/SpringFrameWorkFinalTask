package com.example.finalTask.mapper;

import com.example.finalTask.dto.booking.BookingRequestDto;
import com.example.finalTask.dto.booking.BookingResponseDto;
import com.example.finalTask.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "user", ignore = true)
    Booking toEntity(BookingRequestDto bookingRequestDto);

    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "room.name", target = "roomName")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    BookingResponseDto toDto(Booking booking);
}