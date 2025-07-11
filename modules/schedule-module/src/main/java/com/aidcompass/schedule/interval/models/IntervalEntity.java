package com.aidcompass.schedule.interval.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "work_intervals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntervalEntity {

    @Id
    @SequenceGenerator(name = "w_i_seq", sequenceName = "w_i_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "w_i_seq")
    private Long id;

    @Column(name = "owner_id", nullable = false, updatable = false)
    private UUID ownerId;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "start_t", nullable = false)
    private LocalTime start;

    @Column(name = "end_t", nullable = false )
    private LocalTime end;


    public IntervalEntity(UUID ownerId, LocalTime start, LocalTime end, LocalDate date) {
        this.ownerId = ownerId;
        this.start = start;
        this.end = end;
        this.date = date;
    }
}
