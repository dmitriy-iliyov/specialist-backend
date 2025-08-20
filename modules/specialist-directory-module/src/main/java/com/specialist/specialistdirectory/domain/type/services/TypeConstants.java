package com.specialist.specialistdirectory.domain.type.services;

import com.specialist.specialistdirectory.domain.type.TypeRepository;
import com.specialist.specialistdirectory.exceptions.SpecialistTypeEntityNotFoundByTitleException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
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

    @PostConstruct
    public void init() {
        try {
        OTHER_TYPE_ID = repository.findByTitle(OTHER_TYPE)
                .orElseThrow(SpecialistTypeEntityNotFoundByTitleException::new)
                .getId();
        } catch (Exception e) {
            log.error("Error getting OTHER_TYPE id because of {}", e.getMessage());
            //throw e;
        }
    }
}
