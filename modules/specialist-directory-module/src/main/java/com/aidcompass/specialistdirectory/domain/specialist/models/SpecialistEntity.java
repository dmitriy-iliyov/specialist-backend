package com.aidcompass.specialistdirectory.domain.specialist.models;

import com.aidcompass.specialistdirectory.domain.bookmark.BookmarkEntity;
import com.aidcompass.specialistdirectory.domain.contact.ContactType;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.TypeEntity;
import com.aidcompass.core.general.utils.uuid.UuidFactory;
import com.aidcompass.specialistdirectory.domain.review.models.ReviewEntity;
import com.aidcompass.specialistdirectory.domain.contact.ContactTypeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "specialists")
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"type", "reviews"})
public class SpecialistEntity {

    @Id
    private UUID id;

    @Column(name = "creator_id", nullable = false)
    private UUID creatorId;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "second_name", nullable = false, length = 20)
    private String secondName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private TypeEntity type;

    @Column(name = "suggested_type_id")
    private Long suggestedTypeId;

    @Column(name = "house_number_and_street", nullable = false)
    private String houseNumberAndStreet;

    @Column(name = "city_code")
    private String cityCode;

    @Column(nullable = false)
    private String cityTitle;

    @Column(name = "contact_type", nullable = false)
    @Convert(converter = ContactTypeConverter.class)
    private ContactType contactType;

    @Column(nullable = false)
    private String contact;

    private String site;

    @Column(name = "summary_rating", nullable = false)
    private long summaryRating;

    @Column(name = "total_rating", nullable = false)
    private double totalRating;

    @Column(name = "reviews_count", nullable = false)
    private long reviewsCount;

    @OneToMany(mappedBy = "specialist", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ReviewEntity> reviews;

    @OneToMany(mappedBy = "specialist", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<BookmarkEntity> bookmarks;

    @Version
    private Long version;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    public SpecialistEntity() {
        this.id = UuidFactory.generate();
    }

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
        updatedAt = createdAt;
        reviews = new ArrayList<>();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}
