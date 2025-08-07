package com.specialist.specialistdirectory.domain.specialist.models.dtos;

import com.specialist.specialistdirectory.domain.contact.ContactType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class SpecialistResponseDto {

        private final UUID id;

        @JsonProperty("creator_id")
        private final UUID creatorId;

        @JsonProperty("full_name")
        private final String fullName;

        @JsonProperty("type_title")
        private final String typeTitle;

        @JsonProperty("another_type")
        private String anotherType;

        @JsonProperty("house_number_and_street")
        private final String houseNumberAndStreet;

        @JsonProperty("city_title")
        private final String cityTitle;

        @JsonProperty("city_code")
        private final String cityCode;

        @JsonProperty("contact_type")
        private final ContactType contactType;

        private final String contact;

        private final String site;

        @JsonProperty("total_rating")
        private final double totalRating;

        @JsonProperty("reviews_count")
        private final long reviewsCount;


        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public SpecialistResponseDto(@JsonProperty("id") UUID id,
                                     @JsonProperty("creator_id") UUID creatorId,
                                     @JsonProperty("full_name") String fullName,
                                     @JsonProperty("type_title") String typeTitle,
                                     @JsonProperty("another_type") String anotherType,
                                     @JsonProperty("house_number_and_street") String houseNumberAndStreet,
                                     @JsonProperty("city_title") String cityTitle,
                                     @JsonProperty("city_code") String cityCode,
                                     @JsonProperty("contact_type") ContactType contactType,
                                     @JsonProperty("contact") String contact,
                                     @JsonProperty("site") String site,
                                     @JsonProperty("total_rating") double totalRating,
                                     @JsonProperty("reviews_count") long reviewsCount) {
                this.id = id;
                this.creatorId = creatorId;
                this.fullName = fullName;
                this.typeTitle = typeTitle;
                this.anotherType = anotherType;
                this.houseNumberAndStreet = houseNumberAndStreet;
                this.cityTitle = cityTitle;
                this.cityCode = cityCode;
                this.contactType = contactType;
                this.contact = contact;
                this.site = site;
                this.totalRating = totalRating;
                this.reviewsCount = reviewsCount;
        }
}