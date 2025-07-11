package com.aidcompass.specialistdirectory.domain.specialist.models.markers;

import com.aidcompass.specialistdirectory.domain.contact.ContactType;

public interface ContactHolder {
    ContactType getContactType();
    String getContact();
}
