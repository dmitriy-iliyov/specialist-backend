package com.aidcompass.domain.specialist.markers;

import com.aidcompass.domain.contact.ContactType;

public interface ContactHolder {
    ContactType getContactType();
    String getContact();
}
