package com.aidcompass.core.contact.core.models.markers;


import com.aidcompass.contracts.ContactType;

public interface UpdateDto {
    Long id();
    ContactType type();
    String contact();
    boolean isPrimary();
}
