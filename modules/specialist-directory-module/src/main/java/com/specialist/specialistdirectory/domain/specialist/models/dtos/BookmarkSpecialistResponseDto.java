package com.specialist.specialistdirectory.domain.specialist.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.specialist.specialistdirectory.domain.contact.ContactDto;
import com.specialist.specialistdirectory.domain.contact.ContactType;
import com.specialist.specialistdirectory.domain.language.SpecialistLanguage;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record BookmarkSpecialistResponseDto(
        @JsonProperty("specialist_id") UUID id,

        @JsonProperty("creator_id") UUID creatorId,

        @JsonProperty("full_name") String fullName,

        @JsonProperty("type_title") String typeTitle,

        @JsonProperty("another_type") String anotherType,

        List<SpecialistLanguage> languages,

        @JsonProperty("city_title") String cityTitle,

        @JsonProperty("city_code") String cityCode,

        @JsonProperty("house_number_and_street") String houseNumberAndStreet,

        List<ContactDto> contacts,

        String site,

        @JsonProperty("total_rating") double totalRating,

        @JsonProperty("reviews_count") long reviewsCount
) { }