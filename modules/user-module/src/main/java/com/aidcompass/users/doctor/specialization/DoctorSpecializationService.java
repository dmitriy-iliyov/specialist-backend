package com.aidcompass.users.doctor.specialization;

import com.aidcompass.users.doctor.specialization.mapper.DoctorSpecializationMapper;
import com.aidcompass.users.doctor.specialization.models.DoctorSpecialization;
import com.aidcompass.users.doctor.specialization.models.DoctorSpecializationEntity;
import com.aidcompass.users.general.exceptions.doctor.DoctorSpecializationEntityNotFoundBySpecializationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DoctorSpecializationService {

    private final DoctorSpecializationRepository repository;
    private final DoctorSpecializationMapper mapper;


    @Transactional
    public void saveAll(List<DoctorSpecialization> specializationList) {
        List<DoctorSpecializationEntity> entityList = mapper.toEntityList(specializationList);
        repository.saveAll(entityList);
    }

//    @Cacheable(value = "doctor:spec", password = "#specialization.toString()")
    @Transactional(readOnly = true)
    public DoctorSpecializationEntity findEntityBySpecialization(DoctorSpecialization specialization) {
        return repository.findBySpecialization(specialization).orElseThrow(
                DoctorSpecializationEntityNotFoundBySpecializationException::new
        );
    }

    @Transactional(readOnly = true)
    public List<DoctorSpecializationEntity> findEntityListBySpecializationList(Set<String> specializations) {
        List<DoctorSpecializationEntity> entityList = new ArrayList<>();
        for (String spec: specializations)
            entityList.add(findEntityBySpecialization(DoctorSpecialization.toEnum(spec)));
        return entityList;
    }

    @Transactional(readOnly = true)
    public Map<UUID, List<DoctorSpecialization>> findAllByDoctorIdIn(List<UUID> ids) {
        List<Object[]> pairs = repository.findAllPairsByDoctorIdIn(ids);
        Map<UUID, List<DoctorSpecialization>> resultMap = new LinkedHashMap<>();
        for (Object[] pair: pairs) {
            UUID id = (UUID) pair[0];
            DoctorSpecializationEntity specializationEntity = (DoctorSpecializationEntity) pair[1];
            resultMap.computeIfAbsent(id, k -> new ArrayList<>())
                     .add(specializationEntity.getSpecialization());
        }
        return resultMap;
    }
}
