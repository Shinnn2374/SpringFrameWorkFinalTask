package com.example.finalTask.serivces.Impl;

import com.example.finalTask.dto.room.RoomRequestDto;
import com.example.finalTask.dto.room.RoomResponseDto;
import com.example.finalTask.dto.room.RoomsPageResponse;
import com.example.finalTask.entity.Hotel;
import com.example.finalTask.entity.Room;
import com.example.finalTask.exception.BadRequestException;
import com.example.finalTask.exception.NotFoundException;
import com.example.finalTask.dto.room.RoomFilter;
import com.example.finalTask.filter.RoomSpecification;
import com.example.finalTask.mapper.RoomMapper;
import com.example.finalTask.repository.HotelRepository;
import com.example.finalTask.repository.RoomRepository;
import com.example.finalTask.serivces.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)

    public RoomsPageResponse findAll(RoomFilter filter, Pageable pageable){
        validateDateFilter(filter);

        Specification<Room> spec = Specification.where(RoomSpecification.withId(filter.getId()))
                .and(RoomSpecification.withName(filter.getName()))
                .and(RoomSpecification.withHotelId(filter.getHotelId()))
                .and(RoomSpecification.withMinPrice(filter.getMinPrice()))
                .and(RoomSpecification.withMaxPrice(filter.getMaxPrice()))
                .and(RoomSpecification.withMaxPeople(filter.getMaxPeople()))
                .and(RoomSpecification.availableBetween(filter.getCheckInDate(), filter.getCheckOutDate()));

        Page<Room> page = roomRepository.findAll(spec, (org.springframework.data.domain.Pageable) pageable);

        return RoomsPageResponse.builder()
                .rooms(page.getContent().stream()
                        .map(roomMapper::toDto)
                        .collect(Collectors.toList()))
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPage(page.getNumber())
                .pageSize(page.getSize())
                .build();
    }

    private void validateDateFilter(RoomFilter filter) {
        if ((filter.getCheckInDate() != null && filter.getCheckOutDate() == null) ||
                (filter.getCheckInDate() == null && filter.getCheckOutDate() != null)) {
            throw new BadRequestException("Both check-in and check-out dates must be provided");
        }

        if (filter.getCheckInDate() != null && filter.getCheckOutDate() != null &&
                filter.getCheckInDate().isAfter(filter.getCheckOutDate())) {
            throw new BadRequestException("Check-in date must be before check-out date");
        }
    }
}