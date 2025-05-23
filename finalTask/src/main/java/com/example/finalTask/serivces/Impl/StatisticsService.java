package com.example.finalTask.serivces.Impl;

import com.example.finalTask.dto.statistics.BookingEvent;
import com.example.finalTask.dto.statistics.UserRegistrationEvent;

import java.io.IOException;
import java.time.LocalDate;

public interface StatisticsService {
    void consumeBookingEvent(BookingEvent event);
    void consumeUserRegistrationEvent(UserRegistrationEvent event);
    byte[] generateStatsReport(LocalDate startDate, LocalDate endDate) throws IOException;

}
