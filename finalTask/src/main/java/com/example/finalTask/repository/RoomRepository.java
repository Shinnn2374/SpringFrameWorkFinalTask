package com.example.finalTask.repository;

import com.example.finalTask.dto.room.RoomResponseDto;
import com.example.finalTask.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {

    List<RoomResponseDto> findAllByHotelId(Long id);

    Page<Room> findAll(Specification<Room> spec, Pageable pageable);
}