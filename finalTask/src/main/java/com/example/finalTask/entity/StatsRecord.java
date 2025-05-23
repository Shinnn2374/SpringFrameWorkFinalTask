package com.example.finalTask.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "stats_records")
public class StatsRecord {
    @Id
    private String id;
    private String eventType;
    private Long userId;
    private LocalDate eventDate;
    private BookingEventData bookingData;
    private UserRegistrationEventData userData;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookingEventData {
        private Long roomId;
        private LocalDate checkInDate;
        private LocalDate checkOutDate;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserRegistrationEventData {
        private String registrationSource;
    }
}