package com.example.finalTask.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic bookingEventsTopic() {
        return TopicBuilder.name("booking-events")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic userRegistrationEventsTopic() {
        return TopicBuilder.name("user-registration-events")
                .partitions(3)
                .replicas(1)
                .build();
    }
}