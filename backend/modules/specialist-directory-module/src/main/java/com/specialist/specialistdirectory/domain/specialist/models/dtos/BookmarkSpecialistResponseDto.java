package com.specialist.specialistdirectory.domain.specialist.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.specialist.specialistdirectory.domain.specialist.models.enums.Gender;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistLanguage;

import java.util.List;
import java.util.UUID;

public record BookmarkSpecialistResponseDto(
        @JsonProperty("specialist_id") UUID id,

        @JsonProperty("creator_id") UUID creatorId,

        @JsonProperty("full_name") String fullName,

        Gender gender,

        @JsonProperty("type_title") String typeTitle,

        @JsonProperty("another_type") String anotherType,

        Integer experience,

        List<SpecialistLanguage> languages,

        String details,

        @JsonProperty("city_title") String cityTitle,

        @JsonProperty("city_code") String cityCode,

        String address,

        List<ContactDto> contacts,

        String site,

        @JsonProperty("total_rating") double totalRating,

        @JsonProperty("reviews_count") long reviewsCount
) { }