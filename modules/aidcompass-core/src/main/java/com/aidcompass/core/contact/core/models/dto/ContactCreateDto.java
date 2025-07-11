package com.aidcompass.core.contact.core.models.dto;

import com.aidcompass.contracts.ContactType;
import com.aidcompass.core.contact.core.models.markers.CreateDto;
import com.aidcompass.core.contact.core.validation.annotation.Contact;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Contact
public record ContactCreateDto(
        @NotNull(message = "Contact type shouldn't be null!")
        ContactType type,

        @NotBlank(message = "Contact shouldn't be empty or blank!")
        String contact
) implements CreateDto { }
