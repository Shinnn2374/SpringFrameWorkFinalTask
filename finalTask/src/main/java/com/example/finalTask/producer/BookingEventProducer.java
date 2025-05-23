package com.example.finalTask.producer;

import com.example.finalTask.dto.statistics.BookingEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingEventProducer {
    private final KafkaTemplate<String, BookingEvent> kafkaTemplate;

    public void sendBookingEvent(BookingEvent event) {
        ListenableFuture<SendResult<String, BookingEvent>> future = (ListenableFuture<SendResult<String, BookingEvent>>) kafkaTemplate.send("booking-events", event);

        future.addCallback(
                result -> log.info("Booking event sent: {}", event),
                ex -> log.error("Failed to send booking event", ex)
        );
    }
}