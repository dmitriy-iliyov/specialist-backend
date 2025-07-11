package com.aidcompass.specialistdirectory.domain.specialist_type.services;

import com.aidcompass.specialistdirectory.domain.specialist_type.TypeRepository;
import com.aidcompass.specialistdirectory.exceptions.SpecialistTypeEntityNotFoundByTypeException;
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
                .orElseThrow(SpecialistTypeEntityNotFoundByTypeException::new)
                .getId();
    }


}
