package com.aidcompass.schedule.schedule_filling_progress.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "schedule_progreses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleProgressEntity {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "filled_appointment_duration")
    private boolean filledAppointmentDuration;

    @Column(name = "filled_first_work_day")
    private boolean filledFirstWorkDay = true;

    @Transient
    private boolean wasComplete;


    public ScheduleProgressEntity(UUID userId) {
        this.userId = userId;
    }

    @PostLoad
    public void postLoad() {
        this.setWasComplete(this.isFilledAppointmentDuration() && this.isFilledFirstWorkDay());
    }
}
