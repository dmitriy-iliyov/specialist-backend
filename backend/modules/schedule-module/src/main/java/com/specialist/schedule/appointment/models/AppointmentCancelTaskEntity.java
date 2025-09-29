package com.specialist.schedule.appointment.models;

import com.specialist.schedule.appointment.models.enums.AppointmentCancelTaskType;
import com.specialist.schedule.appointment.repositories.AppointmentCancelTaskTypeConverter;
import com.specialist.utils.UuidUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "appointments_cancel_tasks",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_appointments_cancel_tasks",
                columnNames = {"participant_id", "type", "date"}
        )
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentCancelTaskEntity {

    @Id
    private UUID id;

    @Column(name = "participant_id", nullable = false)
    private UUID participantId;

    @Convert(converter = AppointmentCancelTaskTypeConverter.class)
    @Column(nullable = false)
    private AppointmentCancelTaskType type;

    private LocalDate date;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public AppointmentCancelTaskEntity(UUID participantId, AppointmentCancelTaskType type, LocalDate date) {
        this.participantId = participantId;
        this.type = type;
        this.date = date;
    }

    @PrePersist
    public void prePersist() {
        id = UuidUtils.generateV7();
        createdAt = Instant.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}
