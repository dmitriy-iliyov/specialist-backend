package com.specialist.specialistdirectory.domain.specialist.models.dtos;

import com.specialist.specialistdirectory.domain.contact.ContactType;
import com.specialist.specialistdirectory.domain.specialist.models.markers.SpecialistMarker;
import com.specialist.specialistdirectory.domain.specialist.validation.Specialist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Specialist
@RequiredArgsConstructor
@Data
public class SpecialistUpdateDto implements SpecialistMarker {

    @JsonIgnore
    private UUID id;

    @JsonIgnore
    private UUID creatorId;

    @JsonProperty("first_name")
    @NotBlank(message = "First name is required.")
    @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters.")
    private final String firstName;

    @JsonProperty("second_name")
    @Size(min = 2, max = 20, message = "Second name must be between 2 and 20 characters.")
    private final String secondName;

    @JsonProperty("last_name")
    @NotBlank(message = "Last name is required.")
    @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters.")
    private final String lastName;

    @JsonProperty("type_id")
    @NotNull(message = "Specialist type id is required.")
    @Positive(message = "Specialist type id should be positive.")
    private final Long typeId;

    @JsonProperty("another_type")
    @Size(min = 5, max = 20, message = "Another type must be between 5 and 20 characters.")
    private final String anotherType;

    @JsonProperty("house_number_and_street")
    @NotBlank(message = "House pageNumber and street are required.")
    @Size(min = 2, max = 50, message = "House pageNumber and street must be between 2 and 20 characters.")
    private final String houseNumberAndStreet;

    @JsonProperty("city_title")
    @NotBlank(message = "City title is required.")
    @Size(min = 5, max = 20, message = "City title must be between 2 and 20 characters.")
    private final String cityTitle;

    @JsonProperty("city_code")
    @Pattern(regexp = "\\d{5}", message = "City code should be exactly 5 digits.")
    private final String cityCode;

    @JsonProperty("contact_type")
    private final ContactType contactType;

    @NotBlank(message = "Contact is required.")
    private final String contact;

    @Pattern(regexp = "^(https?:\\/\\/)?([\\w\\-]+\\.)+[\\w\\-]+(\\/\\S*)?$", message = "Site must be a valid URL.")
    private final String site;
}
