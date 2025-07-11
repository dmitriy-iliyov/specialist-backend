package com.aidcompass.aggregator.api.customer;


import com.aidcompass.core.contact.core.models.dto.PrivateContactResponseDto;
import com.aidcompass.users.customer.models.PrivateCustomerResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CustomerPrivateProfileDto(
        @JsonProperty("avatar_url")
        String avatarUrl,

        @JsonProperty("customer_profile")
        PrivateCustomerResponseDto customer,

        @JsonProperty("contacts")
        List<PrivateContactResponseDto> contacts
) { }
