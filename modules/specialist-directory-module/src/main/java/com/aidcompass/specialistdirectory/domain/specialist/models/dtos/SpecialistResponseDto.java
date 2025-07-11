package com.aidcompass.specialistdirectory.domain.specialist.models.dtos;

import com.aidcompass.specialistdirectory.domain.contact.ContactType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Data
public class SpecialistResponseDto {

        private final UUID id;

        @JsonProperty("creator_id")
        private final UUID creatorId;

        @JsonProperty("first_name")
        private final String firstName;

        @JsonProperty("second_name")
        private final String secondName;

        @JsonProperty("last_name")
        private final String lastName;

        @JsonProperty("type_title")
        private final String typeTitle;

        @JsonProperty("another_type")
        private String anotherType;

        @JsonProperty("house_number_and_street")
        private final String houseNumberAndStreet;

        @JsonProperty("city_title")
        private final String cityTitle;

        @JsonProperty("contact_type")
        private final ContactType contactType;

        private final String contact;

        private final String site;

        @JsonProperty("total_rating")
        private final double totalRating;

        @JsonProperty("reviews_count")
        private final long reviewsCount;
}