package com.example.finalTask.dto.room;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomListDto {
    private List<RoomResponseDto> rooms;
    private long totalCount;
}