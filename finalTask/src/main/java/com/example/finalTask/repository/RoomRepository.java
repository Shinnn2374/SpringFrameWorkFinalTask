package com.example.finalTask.repository;

import com.example.finalTask.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {
    List<Room> findByHotelId(Long hotelId);

    @Query("SELECT r FROM Room r WHERE r.hotel.id = :hotelId AND " +
            "NOT EXISTS (SELECT b FROM Booking b WHERE b.room.id = r.id AND " +
            "((b.checkInDate BETWEEN :checkIn AND :checkOut) OR " +
            "(b.checkOutDate BETWEEN :checkIn AND :checkOut) OR " +
            "(b.checkInDate <= :checkIn AND b.checkOutDate >= :checkOut)))")
    List<Room> findAvailableRooms(@Param("hotelId") Long hotelId,
                                  @Param("checkIn") LocalDate checkIn,
                                  @Param("checkOut") LocalDate checkOut);
}