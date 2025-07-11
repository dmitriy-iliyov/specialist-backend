package com.aidcompass.schedule.schedule_filling_progress;

import com.aidcompass.schedule.schedule_filling_progress.models.ScheduleProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScheduleProgressRepository extends JpaRepository<ScheduleProgressEntity, UUID> {
}
