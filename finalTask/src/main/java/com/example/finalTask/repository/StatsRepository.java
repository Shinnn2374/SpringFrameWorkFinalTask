package com.example.finalTask.repository;

import com.example.finalTask.entity.StatsRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StatsRepository extends MongoRepository<StatsRecord, String> {
    List<StatsRecord> findByEventDateBetween(LocalDate start, LocalDate end);
}