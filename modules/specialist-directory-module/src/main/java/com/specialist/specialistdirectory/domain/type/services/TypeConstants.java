package com.specialist.specialistdirectory.domain.type.services;

import com.specialist.specialistdirectory.domain.type.TypeRepository;
import com.specialist.specialistdirectory.exceptions.SpecialistTypeEntityNotFoundByTitleException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public final class TypeConstants {

    private final TypeRepository repository;

    public static String OTHER_TYPE = "OTHER";
    public static Long OTHER_TYPE_ID;

    public TypeConstants(TypeRepository repository) {
        this.repository = repository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void warmUp() {
        try {
        OTHER_TYPE_ID = repository.findByTitle(OTHER_TYPE)
                .orElseThrow(SpecialistTypeEntityNotFoundByTitleException::new)
                .getId();
        log.info("OTHER_TYPE_ID cache successfully wormed up with value={}", OTHER_TYPE_ID);
        } catch (Exception e) {
            log.error("Error warm up OTHER_TYPE_ID because of {}", e.getMessage());
            throw e;
        }
    }
}
