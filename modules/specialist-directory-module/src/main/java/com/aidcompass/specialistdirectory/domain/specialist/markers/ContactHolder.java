package com.aidcompass.specialistdirectory.domain.specialist.markers;

import com.aidcompass.specialistdirectory.domain.contact.ContactType;

public interface ContactHolder {
    ContactType getContactType();
    String getContact();
}
