package com.example.finalTask.serivces.Impl;

import com.example.finalTask.dto.room.RoomRequestDto;
import com.example.finalTask.dto.room.RoomResponseDto;
import com.example.finalTask.entity.Hotel;
import com.example.finalTask.entity.Room;
import com.example.finalTask.exception.NotFoundException;
import com.example.finalTask.mapper.RoomMapper;
import com.example.finalTask.repository.HotelRepository;
import com.example.finalTask.repository.RoomRepository;
import com.example.finalTask.serivces.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomMapper roomMapper;

    public RoomResponseDto findById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room not found with id: " + id));
        return roomMapper.toDto(room);
    }

    public List<RoomResponseDto> findAllByHotelId(Long hotelId) {
        return roomRepository.findAllByHotelId(hotelId).stream()
                .map(roomMapper::toDto)
                .toList();
    }

    @Transactional
    public RoomResponseDto create(RoomRequestDto roomRequestDto) {
        Hotel hotel = hotelRepository.findById(roomRequestDto.getHotelId())
                .orElseThrow(() -> new NotFoundException("Hotel not found with id: " + roomRequestDto.getHotelId()));

        Room room = roomMapper.toEntity(roomRequestDto);
        room.setHotel(hotel);
        Room savedRoom = roomRepository.save(room);
        return roomMapper.toDto(savedRoom);
    }

    @Transactional
    public RoomResponseDto update(Long id, RoomRequestDto roomRequestDto) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room not found with id: " + id));

        if (!room.getHotel().getId().equals(roomRequestDto.getHotelId())) {
            Hotel hotel = hotelRepository.findById(roomRequestDto.getHotelId())
                    .orElseThrow(() -> new NotFoundException("Hotel not found with id: " + roomRequestDto.getHotelId()));
            room.setHotel(hotel);
        }

        roomMapper.updateEntityFromDto(roomRequestDto, room);
        Room updatedRoom = roomRepository.save(room);
        return roomMapper.toDto(updatedRoom);
    }

    @Transactional
    public void delete(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new NotFoundException("Room not found with id: " + id);
        }
        roomRepository.deleteById(id);
    }
}