package com.aidcompass.specialistdirectory.infrastructure;

import com.aidcompass.specialistdirectory.domain.type.TypeRepository;
import com.aidcompass.specialistdirectory.domain.type.models.TypeEntity;
import com.aidcompass.specialistdirectory.domain.type.services.TypeConstants;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("dev_v2")
@Component
@RequiredArgsConstructor
public class TypeDbInitializer {

    private final TypeRepository repository;


    @PostConstruct
    public void setUp() {
        if (repository.count() == 0) {
            List<TypeEntity> defaultTypes = List.of(
                    new TypeEntity("Plumber", true),
                    new TypeEntity("Electrician", true),
                    new TypeEntity("Carpenter", true),
                    new TypeEntity("Locksmith", true),
                    new TypeEntity("Painter", true),
                    new TypeEntity("Groomer", true),
                    new TypeEntity("Orthodontist", true),
                    new TypeEntity("Manicurist", true),
                    new TypeEntity(TypeConstants.OTHER_TYPE, true)
            );

            repository.saveAll(defaultTypes);
        }
    }
}
