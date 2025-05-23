package com.example.finalTask.dto.room;

import com.example.finalTask.dto.room.RoomResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomsPageResponse {
    private List<RoomResponseDto> rooms;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;
}