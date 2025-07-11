package com.aidcompass.core.contact.contact_filling_progress;

import com.aidcompass.core.contact.contact_filling_progress.models.ContactProgressEntity;
import com.aidcompass.core.contact.contact_filling_progress.models.ContactFilledEvent;
import com.aidcompass.core.contact.exceptions.not_found.ContactProgressEntityNotFoundByUserIdException;
import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.security.domain.authority.models.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactProgressService {

    private final ContactProgressRepository repository;
    private final ApplicationEventPublisher publisher;


    @Transactional
    public void emailFilled(UUID userId, Authority authority) {
        ContactProgressEntity entity;
        try {
            entity = repository.findById(userId).orElseThrow(ContactProgressEntityNotFoundByUserIdException::new);
            entity.setFilledPhone(true);
        } catch (BaseNotFoundException e) {
            entity = new ContactProgressEntity(userId);
            entity.setFilledEmail(true);
        }
        boolean isComplete = entity.isFilledEmail() && entity.isFilledPhone();
        if (!entity.isWasComplete() && isComplete) {
            publisher.publishEvent(new ContactFilledEvent(userId, authority));
        }
        repository.save(entity);
    }

    @Transactional
    public void phoneFilled(UUID userId, Authority authority) {
        ContactProgressEntity entity;
        try {
            entity = repository.findById(userId).orElseThrow(ContactProgressEntityNotFoundByUserIdException::new);
            entity.setFilledPhone(true);
        } catch (BaseNotFoundException e) {
            entity = new ContactProgressEntity(userId);
            entity.setFilledPhone(true);
        }
        boolean isComplete = entity.isFilledEmail() && entity.isFilledPhone();
        if (!entity.isWasComplete() && isComplete) {
            publisher.publishEvent(new ContactFilledEvent(userId, authority));
        }
        repository.save(entity);
    }

    @Cacheable(value = "contacts:progress", key = "#ownerId")
    @Transactional(readOnly = true)
    public boolean isComplete(UUID ownerId) {
        return repository.isComplete(ownerId);
    }
}
