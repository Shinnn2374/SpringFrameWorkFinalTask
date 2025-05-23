package com.example.finalTask.producer;

import com.example.finalTask.dto.statistics.UserRegistrationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRegistrationEventProducer {
    private final KafkaTemplate<String, UserRegistrationEvent> kafkaTemplate;

    public void sendUserRegistrationEvent(UserRegistrationEvent event) {
        ListenableFuture<SendResult<String, UserRegistrationEvent>> future = (ListenableFuture<SendResult<String, UserRegistrationEvent>>) kafkaTemplate.send("user-registration-events", event);

        future.addCallback(
                result -> log.info("User registration event sent: {}", event),
                ex -> log.error("Failed to send user registration event", ex)
        );
    }
}