package com.specialist.specialistdirectory.domain.specialist.models.dtos;

import com.specialist.specialistdirectory.domain.contact.ContactType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record BookmarkSpecialistResponseDto(
        @JsonProperty("specialist_id") UUID id,

        @JsonProperty("creator_id") UUID creatorId,

        @JsonProperty("full_name") String fullName,

        @JsonProperty("type_title") String typeTitle,

        @JsonProperty("another_type") String anotherType,

        @JsonProperty("house_number_and_street") String houseNumberAndStreet,

        @JsonProperty("city_title") String cityTitle,

        @JsonProperty("city_code") String cityCode,

        @JsonProperty("contact_type") ContactType contactType,

        String contact,

        String site,

        @JsonProperty("total_rating") double totalRating,

        @JsonProperty("reviews_count") long reviewsCount
) { }