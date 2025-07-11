package com.aidcompass.core.contact.contact_type;

import com.aidcompass.contracts.ContactType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Profile("dev")
@Component
@Slf4j
@RequiredArgsConstructor
public class ContactTypeDatabaseInitializer {

    private final ContactTypeService contactTypeService;


    @PostConstruct
    public void setListOfContactType() {
        try {
            contactTypeService.saveAll(new ArrayList<>(List.of(ContactType.EMAIL, ContactType.PHONE_NUMBER)));
        } catch (Exception e) {
            log.error("Error when add ContactType to database: {}", e.getMessage());
        }
    }
}
