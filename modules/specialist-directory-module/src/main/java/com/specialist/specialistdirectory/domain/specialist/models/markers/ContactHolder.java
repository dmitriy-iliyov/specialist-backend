package com.specialist.specialistdirectory.domain.specialist.models.markers;

import com.specialist.specialistdirectory.domain.contact.ContactType;

public interface ContactHolder {
    ContactType getContactType();
    String getContact();
}
