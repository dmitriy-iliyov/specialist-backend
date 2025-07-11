package com.aidcompass.core.contact.contact_type.models;

import com.aidcompass.contracts.ContactType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "contact_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactTypeEntity {

    @Id
    @SequenceGenerator(name = "cont_t_seq", sequenceName = "cont_t_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cont_t_seq")
    private Integer id;

    @Column(name = "type", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private ContactType type;
}
