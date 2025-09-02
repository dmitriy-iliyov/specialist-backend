package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.specialist.specialistdirectory.domain.specialist.models.StatisticEntity;
import com.specialist.specialistdirectory.domain.specialist.models.enums.ApproverType;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import com.specialist.specialistdirectory.domain.specialist.repositories.SpecialistRepository;
import com.specialist.specialistdirectory.exceptions.SpecialistNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecialistStatusServiceImpl implements SpecialistStatusService {

    private final SpecialistRepository repository;

    @CacheEvict(value = "specialists:created:count:total", key = "#id")
    @Transactional
    @Override
    public void approve(UUID id, UUID approverId, ApproverType approverType) {
        SpecialistEntity entity = repository.findWithStatisticById(id).orElseThrow(SpecialistNotFoundByIdException::new);
        StatisticEntity statisticEntity = entity.getStatistic();
        statisticEntity.setApproverId(approverId);
        statisticEntity.setApproverType(approverType);
        entity.setStatus(SpecialistStatus.APPROVED);
        repository.save(entity);
    }

    @Transactional
    @Override
    public void manage(UUID id, UUID ownerId) {
        repository.updateStatusAndOwnerIdById(id, ownerId, SpecialistStatus.MANAGED);
    }

    @Transactional
    @Override
    public void recall(UUID id) {
        repository.updateStatusById(id, SpecialistStatus.RECALLED);
    }
}
