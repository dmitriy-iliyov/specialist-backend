package com.specialist.schedule.interval.repository;

import com.specialist.schedule.interval.models.NearestIntervalEntity;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NearestIntervalRepository extends KeyValueRepository<NearestIntervalEntity, UUID> { }