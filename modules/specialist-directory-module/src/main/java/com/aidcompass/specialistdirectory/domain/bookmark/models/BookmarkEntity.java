package com.aidcompass.specialistdirectory.domain.bookmark.models;

import com.aidcompass.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.aidcompass.utils.UuidUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(
        name = "bookmarks",
        uniqueConstraints = @UniqueConstraint(columnNames = {"owner_id", "specialist_id"})
)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(exclude = {"specialist"})
public class BookmarkEntity {

    @Id
    private UUID id;

    @Column(name = "owner_id", nullable = false, updatable = false)
    private UUID ownerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialist_id", nullable = false)
    private SpecialistEntity specialist;


    public BookmarkEntity(UUID ownerId, SpecialistEntity specialist) {
        this.id = UuidUtils.generateV7();
        this.ownerId = ownerId;
        this.specialist = specialist;
    }
}