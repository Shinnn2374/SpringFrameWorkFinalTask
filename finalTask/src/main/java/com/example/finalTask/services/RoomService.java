package com.example.finalTask.services;

import com.example.finalTask.entity.Room;
import com.example.finalTask.repository.HotelRepository;
import com.example.finalTask.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomMapper roomMapper;

    // ... существующие методы

    public RoomResponseDto addUnavailableDates(Long roomId, Set<LocalDate> dates) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        room.getUnavailableDates().addAll(dates);
        Room updatedRoom = roomRepository.save(room);
        return roomMapper.toDto(updatedRoom);
    }

    public RoomResponseDto removeUnavailableDates(Long roomId, Set<LocalDate> dates) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        room.getUnavailableDates().removeAll(dates);
        Room updatedRoom = roomRepository.save(room);
        return roomMapper.toDto(updatedRoom);
    }

    public List<RoomResponseDto> getAvailableRooms(Long hotelId, LocalDate checkIn, LocalDate checkOut) {
        if (checkIn.isAfter(checkOut)) {
            throw new BadRequestException("Check-out date must be after check-in date");
        }

        List<Room> rooms = roomRepository.findAvailableRooms(hotelId, checkIn, checkOut);
        return rooms.stream()
                .map(roomMapper::toDto)
                .collect(Collectors.toList());
    }
}