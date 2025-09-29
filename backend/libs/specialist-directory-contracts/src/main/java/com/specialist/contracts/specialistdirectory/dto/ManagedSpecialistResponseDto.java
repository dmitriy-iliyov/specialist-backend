package com.specialist.contracts.specialistdirectory.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.UUID;

/**
 * Transport DTO for specialist data between modules.
 * Complex fields (jsonLanguages, jsonContacts) are pre-serialized as JSON strings.
 */
@Getter
public class ManagedSpecialistResponseDto {

    private final UUID id;

    @JsonProperty("owner_id")
    private final UUID ownerId;

    @JsonProperty("full_name")
    private final String fullName;

    @JsonProperty("type_title")
    private final String typeTitle;

    @JsonProperty("another_type")
    private String anotherType;

    @JsonProperty("json_languages")
    private final String jsonLanguages;

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
    public ManagedSpecialistResponseDto(@JsonProperty("id") UUID id,
                                        @JsonProperty("owner_id") UUID ownerId,
                                        @JsonProperty("full_name") String fullName,
                                        @JsonProperty("type_title") String typeTitle,
                                        @JsonProperty("another_type") String anotherType,
                                        @JsonProperty("json_languages") String jsonLanguages,
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
        this.typeTitle = typeTitle;
        this.anotherType = anotherType;
        this.jsonLanguages = jsonLanguages;
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
