package com.specialist.specialistdirectory.domain.type.services;

import com.specialist.specialistdirectory.domain.type.TypeRepository;
import com.specialist.specialistdirectory.exceptions.SpecialistTypeEntityNotFoundByTitleException;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class TypeConstants {

    private final TypeRepository repository;

    public static String OTHER_TYPE = "OTHER";
    public static Long OTHER_TYPE_ID;

    public TypeConstants(TypeRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        OTHER_TYPE_ID = repository.findByTitle(OTHER_TYPE)
                .orElseThrow(SpecialistTypeEntityNotFoundByTitleException::new)
                .getId();
    }


}
