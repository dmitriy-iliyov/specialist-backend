package com.aidcompass.users.jurist.specialization;

import com.aidcompass.users.general.exceptions.jurist.JuristSpecializationEntityNotFoundBySpecializationException;
import com.aidcompass.users.jurist.specialization.mapper.JuristSpecializationMapper;
import com.aidcompass.users.jurist.specialization.models.JuristSpecialization;
import com.aidcompass.users.jurist.specialization.models.JuristSpecializationEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class JuristSpecializationService {

    private final JuristSpecializationRepository repository;
    private final JuristSpecializationMapper mapper;
    private final ObjectMapper objectMapper;


    @Transactional
    public void saveAll(List<JuristSpecialization> specializationList) {
        List<JuristSpecializationEntity> entityList = mapper.toEntityList(specializationList);
        repository.saveAll(entityList);
    }

    @Transactional(readOnly = true)
    public JuristSpecializationEntity findEntityBySpecialization(JuristSpecialization specialization) {
        if (specialization == null) {
            return null;
        }
        return repository.findBySpecialization(specialization).orElseThrow(
                JuristSpecializationEntityNotFoundBySpecializationException::new
        );
    }

    @Transactional(readOnly = true)
    public List<JuristSpecializationEntity> findEntityListBySpecializationList(Set<String> specializations) {
        List<JuristSpecializationEntity> entityList = new ArrayList<>();
        for (String spec: specializations)
            entityList.add(findEntityBySpecialization(JuristSpecialization.toEnum(spec)));
        return entityList;
    }

    @Transactional(readOnly = true)
    public Map<UUID, List<JuristSpecialization>> findAllByJuristIdIn(List<UUID> ids) {
        List<Object[]> pairList = repository.findAllPairsByJuristIdIn(ids);
        Map<UUID, List<JuristSpecialization>> specializationsMap = new HashMap<>();
        for (Object[] pair: pairList) {
            UUID id = (UUID) pair[0];
            JuristSpecializationEntity specializationEntity = (JuristSpecializationEntity) pair[1];
            specializationsMap.computeIfAbsent(id, k -> new ArrayList<>())
                              .add(specializationEntity.getSpecialization());
        }
        return specializationsMap;
    }
}
