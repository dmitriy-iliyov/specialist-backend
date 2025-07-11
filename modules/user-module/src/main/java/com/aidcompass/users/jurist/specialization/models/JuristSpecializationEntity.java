package com.aidcompass.users.jurist.specialization.models;


import com.aidcompass.users.jurist.models.JuristEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "jurist_specializations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JuristSpecializationEntity {

    @Id
    @SequenceGenerator(name = "jur_spec_seq", sequenceName = "jur_spec_seq", allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jur_spec_seq")
    private Integer id;

    @Column(name = "specialization", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private JuristSpecialization specialization;

    @ManyToMany(mappedBy = "specializations", fetch = FetchType.LAZY)
    private Set<JuristEntity> jurists;
}
