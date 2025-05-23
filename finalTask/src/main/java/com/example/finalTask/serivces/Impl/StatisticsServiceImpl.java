package com.example.finalTask.serivces.Impl;

import com.example.finalTask.dto.statistics.BookingEvent;
import com.example.finalTask.dto.statistics.UserRegistrationEvent;
import com.example.finalTask.entity.StatsRecord;
import com.example.finalTask.repository.StatsRepository;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final StatsRepository statsRepository;

    @KafkaListener(topics = "booking-events", groupId = "hotel-stats-group")
    public void consumeBookingEvent(BookingEvent event) {
        StatsRecord record = StatsRecord.builder()
                .eventType("BOOKING")
                .userId(event.getUserId())
                .eventDate(LocalDate.now())
                .bookingData(StatsRecord.BookingEventData.builder()
                        .roomId(event.getRoomId())
                        .checkInDate(event.getCheckInDate())
                        .checkOutDate(event.getCheckOutDate())
                        .build())
                .build();

        statsRepository.save(record);
        log.info("Saved booking event: {}", event);
    }

    @KafkaListener(topics = "user-registration-events", groupId = "hotel-stats-group")
    public void consumeUserRegistrationEvent(UserRegistrationEvent event) {
        StatsRecord record = StatsRecord.builder()
                .eventType("REGISTRATION")
                .userId(event.getUserId())
                .eventDate(LocalDate.now())
                .userData(StatsRecord.UserRegistrationEventData.builder()
                        .registrationSource("WEB")
                        .build())
                .build();

        statsRepository.save(record);
        log.info("Saved registration event: {}", event);
    }

    public byte[] generateStatsReport(LocalDate startDate, LocalDate endDate) throws IOException {
        List<StatsRecord> records = statsRepository.findByEventDateBetween(startDate, endDate);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        CSVWriter csvWriter = new CSVWriter(writer);

        // Заголовки CSV
        csvWriter.writeNext(new String[]{
                "Event Type", "User ID", "Event Date",
                "Room ID", "Check-In Date", "Check-Out Date",
                "Registration Source"
        });

        // Данные
        for (StatsRecord record : records) {
            csvWriter.writeNext(new String[]{
                    record.getEventType(),
                    record.getUserId().toString(),
                    record.getEventDate().toString(),
                    record.getBookingData() != null ? record.getBookingData().getRoomId().toString() : "",
                    record.getBookingData() != null ? record.getBookingData().getCheckInDate().toString() : "",
                    record.getBookingData() != null ? record.getBookingData().getCheckOutDate().toString() : "",
                    record.getUserData() != null ? record.getUserData().getRegistrationSource() : ""
            });
        }

        csvWriter.close();
        return outputStream.toByteArray();
    }
}