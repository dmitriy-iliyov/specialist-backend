package com.aidcompass.core.contact.core.facades;

import com.aidcompass.core.contact.core.models.dto.ContactUpdateDto;

@FunctionalInterface
public interface ContactChangingListener {
    void callback(ContactUpdateDto contact);
}
