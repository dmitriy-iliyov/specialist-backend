package com.aidcompass.specialistdirectory.domain.bookmark;

import com.aidcompass.core.general.utils.uuid.UuidFactory;
import com.aidcompass.specialistdirectory.domain.specialist.models.SpecialistEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "bookmarks")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookmarkEntity {

    @Id
    private UUID id;

    @Column(name = "owner_id", nullable = false, updatable = false)
    private UUID ownerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialist_id", nullable = false)
    private SpecialistEntity specialist;


    public BookmarkEntity(UUID ownerId, SpecialistEntity specialist) {
        this.id = UuidFactory.generate();
        this.ownerId = ownerId;
        this.specialist = specialist;
    }
}