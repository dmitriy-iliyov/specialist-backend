package com.aidcompass.core.contact.contact_type;

import com.aidcompass.contracts.ContactType;
import com.aidcompass.core.contact.contact_type.models.ContactTypeDto;
import com.aidcompass.core.contact.contact_type.models.ContactTypeEntity;

import java.util.List;

public interface ContactTypeService {

    List<ContactTypeDto> saveAll(List<ContactType> contactTypeList);

    ContactTypeEntity findByType(ContactType type);
}
