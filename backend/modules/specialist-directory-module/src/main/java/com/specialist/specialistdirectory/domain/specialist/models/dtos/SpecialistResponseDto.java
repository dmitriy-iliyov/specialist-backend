package com.specialist.specialistdirectory.domain.specialist.models.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistLanguage;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistState;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import lombok.Data;

import java.util.List;
import java.util.UUID;

// FIXME : add if specialist is managed return actual specialist id

@Data
public class SpecialistResponseDto {

        private final UUID id;

        @JsonProperty("owner_id")
        private final UUID ownerId;

        @JsonProperty("full_name")
        private final String fullName;

        @JsonProperty("type_title")
        private final String typeTitle;

        @JsonProperty("another_type")
        private String anotherType;

        private final List<SpecialistLanguage> languages;

        @JsonProperty("city_title")
        private final String cityTitle;

        @JsonProperty("city_code")
        private final String cityCode;

        private final String address;

        private final List<ContactDto> contacts;

        private final String site;

        private final SpecialistStatus status;

        private final SpecialistState state;

        @JsonProperty("total_rating")
        private final double totalRating;

        @JsonProperty("reviews_count")
        private final long reviewsCount;


        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public SpecialistResponseDto(@JsonProperty("id") UUID id,
                                     @JsonProperty("owner_id") UUID ownerId,
                                     @JsonProperty("full_name") String fullName,
                                     @JsonProperty("type_title") String typeTitle,
                                     @JsonProperty("another_type") String anotherType,
                                     @JsonProperty("languages") List<SpecialistLanguage> languages,
                                     @JsonProperty("city_title") String cityTitle,
                                     @JsonProperty("city_code") String cityCode,
                                     @JsonProperty("address") String address,
                                     @JsonProperty("contacts") List<ContactDto> contacts,
                                     @JsonProperty("site") String site,
                                     @JsonProperty("status") SpecialistStatus status,
                                     @JsonProperty("state") SpecialistState state,
                                     @JsonProperty("total_rating") double totalRating,
                                     @JsonProperty("reviews_count") long reviewsCount) {
                this.id = id;
                this.ownerId = ownerId;
                this.fullName = fullName;
                this.typeTitle = typeTitle;
                this.anotherType = anotherType;
                this.languages = languages;
                this.cityTitle = cityTitle;
                this.cityCode = cityCode;
                this.address = address;
                this.contacts = contacts;
                this.site = site;
                this.status = status;
                this.state = state;
                this.totalRating = totalRating;
                this.reviewsCount = reviewsCount;
        }
}