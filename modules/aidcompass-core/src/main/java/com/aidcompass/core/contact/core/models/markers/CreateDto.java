package com.aidcompass.core.contact.core.models.markers;


import com.aidcompass.contracts.ContactType;

public interface CreateDto {
    ContactType type();
    String contact();
}
