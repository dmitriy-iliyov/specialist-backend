package com.specialist.specialistdirectory.domain.specialist.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistLanguage;
import com.specialist.specialistdirectory.domain.specialist.models.markers.SpecialistMarker;
import com.specialist.specialistdirectory.domain.specialist.validation.Contact;
import com.specialist.specialistdirectory.domain.specialist.validation.Specialist;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
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

    @NotEmpty(message = "At least one language required.")
    private final List<SpecialistLanguage> languages;

    @JsonProperty("city_title")
    @NotBlank(message = "City title is required.")
    @Size(min = 5, max = 20, message = "City title must be between 5 and 20 characters.")
    private final String cityTitle;

    @JsonProperty("city_code")
    @Pattern(regexp = "\\d{5}", message = "City code should be exactly 5 digits.")
    private final String cityCode;

    @NotBlank(message = "Street ius required.")
    private String street;

    @JsonProperty("house_number")
    @NotBlank(message = "Number of house is required.")
    private String houseNumber;

    @Valid
    private final List< @Contact ContactDto> contacts;

    @Pattern(regexp = "^(https?:\\/\\/)?([\\w\\-]+\\.)+[\\w\\-]+(\\/\\S*)?$", message = "Site must be a valid URL.")
    private final String site;
}
