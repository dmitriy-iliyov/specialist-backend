package com.aidcompass.users.jurist.specialization.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jurist_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JuristTypeEntity {

    @Id
    @SequenceGenerator(name = "jur_type_seq", sequenceName = "jur_type_seq", initialValue = 1, allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jur_type_seq")
    private Integer id;

    @Column(name = "type", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private JuristType type;
}
