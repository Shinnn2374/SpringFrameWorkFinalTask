package com.example.finalTask.serivces.Impl;

import com.example.finalTask.dto.booking.BookingRequestDto;
import com.example.finalTask.dto.booking.BookingResponseDto;
import com.example.finalTask.entity.Booking;
import com.example.finalTask.entity.Room;
import com.example.finalTask.entity.User;
import com.example.finalTask.exception.BadRequestException;
import com.example.finalTask.exception.NotFoundException;
import com.example.finalTask.mapper.BookingMapper;
import com.example.finalTask.repository.BookingRepository;
import com.example.finalTask.repository.RoomRepository;
import com.example.finalTask.repository.UserRepository;
import com.example.finalTask.serivces.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;

    public List<BookingResponseDto> findAll() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toDto)
                .toList();
    }

    public List<BookingResponseDto> findAllByUserId(Long userId) {
        return bookingRepository.findAllByUserId(userId).stream()
                .map(bookingMapper::toDto)
                .toList();
    }

    @Transactional
    public BookingResponseDto create(BookingRequestDto bookingRequestDto) {
        validateBookingDates(bookingRequestDto);

        Room room = roomRepository.findById(bookingRequestDto.getRoomId())
                .orElseThrow(() -> new NotFoundException("Room not found with id: " + bookingRequestDto.getRoomId()));

        User user = userRepository.findById(bookingRequestDto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with id: " + bookingRequestDto.getUserId()));

        if (isRoomBooked(bookingRequestDto.getRoomId(),
                bookingRequestDto.getCheckInDate(),
                bookingRequestDto.getCheckOutDate())) {
            throw new BadRequestException("Room is already booked for selected dates");
        }

        Booking booking = bookingMapper.toEntity(bookingRequestDto);
        booking.setRoom(room);
        booking.setUser(user);

        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapper.toDto(savedBooking);
    }

    private void validateBookingDates(BookingRequestDto bookingRequestDto) {
        if (bookingRequestDto.getCheckInDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Check-in date cannot be in the past");
        }

        if (bookingRequestDto.getCheckOutDate().isBefore(bookingRequestDto.getCheckInDate())) {
            throw new BadRequestException("Check-out date must be after check-in date");
        }
    }

    private boolean isRoomBooked(Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        return bookingRepository.existsByRoomIdAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(
                roomId, checkOutDate, checkInDate);
    }
}