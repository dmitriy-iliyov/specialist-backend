package com.specialist.contracts.specialistdirectory.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.UUID;

/**
 * Transport DTO for specialist data between modules.
 * Complex fields (jsonLanguages, jsonContacts) and enums are pre-serialized as JSON strings.
 */
@Getter
public class ExternalManagedSpecialistResponseDto {

    private final UUID id;

    @JsonProperty("owner_id")
    private final UUID ownerId;

    @JsonProperty("full_name")
    private final String fullName;

    private final String gender;

    @JsonProperty("type_title")
    private final String typeTitle;

    @JsonProperty("another_type")
    private String anotherType;

    private final Integer experience;

    @JsonProperty("json_languages")
    private final String jsonLanguages;

    private final String details;

    @JsonProperty("city_title")
    private final String cityTitle;

    @JsonProperty("city_code")
    private final String cityCode;

    private final String address;

    @JsonProperty("json_contacts")
    private final String jsonContacts;

    private final String site;

    private final String status;

    @JsonProperty("total_rating")
    private final double totalRating;

    @JsonProperty("reviews_count")
    private final long reviewsCount;


    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ExternalManagedSpecialistResponseDto(@JsonProperty("id") UUID id,
                                                @JsonProperty("owner_id") UUID ownerId,
                                                @JsonProperty("full_name") String fullName,
                                                @JsonProperty("gender") String gender,
                                                @JsonProperty("type_title") String typeTitle,
                                                @JsonProperty("another_type") String anotherType,
                                                @JsonProperty("experience") Integer experience,
                                                @JsonProperty("json_languages") String jsonLanguages,
                                                @JsonProperty("details") String details,
                                                @JsonProperty("city_title") String cityTitle,
                                                @JsonProperty("city_code") String cityCode,
                                                @JsonProperty("address") String address,
                                                @JsonProperty("json_contacts") String jsonContacts,
                                                @JsonProperty("site") String site,
                                                @JsonProperty("status") String status,
                                                @JsonProperty("total_rating") double totalRating,
                                                @JsonProperty("reviews_count") long reviewsCount) {
        this.id = id;
        this.ownerId = ownerId;
        this.fullName = fullName;
        this.gender = gender;
        this.typeTitle = typeTitle;
        this.anotherType = anotherType;
        this.experience = experience;
        this.jsonLanguages = jsonLanguages;
        this.details = details;
        this.cityTitle = cityTitle;
        this.cityCode = cityCode;
        this.address = address;
        this.jsonContacts = jsonContacts;
        this.site = site;
        this.status = status;
        this.totalRating = totalRating;
        this.reviewsCount = reviewsCount;
    }
}
