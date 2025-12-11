package com.specialist.specialistdirectory.domain.specialist.models;

import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkEntity;
import com.specialist.specialistdirectory.domain.review.models.ReviewEntity;
import com.specialist.specialistdirectory.domain.specialist.mappers.SpecialistStateConverter;
import com.specialist.specialistdirectory.domain.specialist.mappers.SpecialistStatusConverter;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.ContactDto;
import com.specialist.specialistdirectory.domain.specialist.models.enums.Gender;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistLanguage;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistState;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import com.specialist.specialistdirectory.domain.type.models.TypeEntity;
import com.specialist.utils.UuidUtils;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Check;
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
@ToString(exclude = {"type", "info", "reviews", "bookmarks"})
public class SpecialistEntity {

    @Id
    private UUID id;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(name = "creator_id", nullable = false, updatable = false)
    private UUID creatorId;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "second_name", length = 20)
    private String secondName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private TypeEntity type;

    @Column(name = "suggested_type_id")
    private Long suggestedTypeId;

    @Check(constraints = "experience >= 0")
    private Integer experience;

    @Type(JsonBinaryType.class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<SpecialistLanguage> languages = new ArrayList<>();

    @Column(length = 300)
    private String details;

    @Column(name = "city_code")
    private String cityCode;

    @Column(name = "city_title", nullable = false)
    private String cityTitle;

    @Column(nullable = false)
    private String street;

    @Column(name = "house_number", nullable = false)
    private String houseNumber;

    @Type(JsonBinaryType.class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<ContactDto> contacts = new ArrayList<>();

    private String site;

    @Column(nullable = false)
    @Convert(converter = SpecialistStatusConverter.class)
    private SpecialistStatus status;

    @Column(nullable = false)
    @Convert(converter = SpecialistStateConverter.class)
    private SpecialistState state;

    @Column(nullable = false)
    private double rating;

    @Column(name = "summary_rating", nullable = false)
    private long summaryRating;

    @Column(name = "reviews_count", nullable = false)
    private long reviewsCount;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "info_id", nullable = false)
    private SpecialistInfoEntity info;

    @OneToMany(mappedBy = "specialist", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ReviewEntity> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "specialist", fetch = FetchType.LAZY)
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