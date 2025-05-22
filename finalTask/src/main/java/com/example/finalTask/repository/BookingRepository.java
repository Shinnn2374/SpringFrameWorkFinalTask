package com.example.finalTask.repository;

import com.example.finalTask.entity.Booking;
import com.example.finalTask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByRoomId(Long roomId);
    List<Booking> findAllByUserId(Long userId);
    boolean existsByRoomIdAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(
            Long roomId, LocalDate checkOutDate, LocalDate checkInDate);
}