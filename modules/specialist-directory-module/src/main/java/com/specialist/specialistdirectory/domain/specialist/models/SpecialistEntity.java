package com.specialist.specialistdirectory.domain.specialist.models;

import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkEntity;
import com.specialist.specialistdirectory.domain.review.models.ReviewEntity;
import com.specialist.specialistdirectory.domain.specialist.mappers.ApproverTypeConverter;
import com.specialist.specialistdirectory.domain.specialist.mappers.CreatorTypeConverter;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.ContactDto;
import com.specialist.specialistdirectory.domain.specialist.models.enums.ApproverType;
import com.specialist.specialistdirectory.domain.specialist.models.enums.CreatorType;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistLanguage;
import com.specialist.specialistdirectory.domain.type.models.TypeEntity;
import com.specialist.utils.UuidUtils;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "specialists")
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"type", "reviews", "bookmarks"})
public class SpecialistEntity {

    @Id
    private UUID id;

    @Column(name = "creator_id", nullable = false, updatable = false)
    private UUID creatorId;

    @Column(name = "creator_type", nullable = false, updatable = false)
    @Convert(converter = CreatorTypeConverter.class)
    private CreatorType creatorType;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "second_name", length = 20)
    private String secondName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private TypeEntity type;

    @Column(name = "suggested_type_id")
    private Long suggestedTypeId;

    @Type(JsonBinaryType.class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<SpecialistLanguage> languages = new ArrayList<>();

    @Column(name = "city_code")
    private String cityCode;

    @Column(name = "city_title", nullable = false)
    private String cityTitle;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String houseNumber;

    @Type(JsonBinaryType.class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<ContactDto> contacts = new ArrayList<>();

    private String site;

    @Column(name = "approved", nullable = false)
    private boolean approved;

    @Column(name = "approver_id")
    private UUID approverId;

    @Column(name = "approver_type")
    @Convert(converter = ApproverTypeConverter.class)
    private ApproverType approverType;

    @Column(nullable = false)
    private double rating;

    @Column(name = "summary_rating", nullable = false)
    private long summaryRating;

    @Column(name = "reviews_count", nullable = false)
    private long reviewsCount;

    @OneToMany(mappedBy = "specialist", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ReviewEntity> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "specialist", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<BookmarkEntity> bookmarks = new ArrayList<>();

    @Version
    private Long version;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    public SpecialistEntity() {
        this.id = UuidUtils.generateV7();
    }

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }

    public String getFullName() {
        if(secondName == null)
            return lastName + " " + firstName;
        return lastName + " " + firstName + " " + secondName;
    }

    public String getAddress() {
        return street + ", " + houseNumber;
    }
}
