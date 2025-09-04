package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.specialist.specialistdirectory.domain.specialist.models.StatisticEntity;
import com.specialist.specialistdirectory.domain.specialist.models.enums.ApproverType;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import com.specialist.specialistdirectory.domain.specialist.repositories.SpecialistRepository;
import com.specialist.specialistdirectory.exceptions.*;
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
        SpecialistEntity specialistEntity = repository.findWithStatisticById(id).orElseThrow(SpecialistNotFoundByIdException::new);
        if (!specialistEntity.getStatus().equals(SpecialistStatus.UNAPPROVED)) {
            throw new UnableSpecialistApproveException();
        }
        StatisticEntity statisticEntity = specialistEntity.getStatistic();
        statisticEntity.setApproverId(approverId);
        statisticEntity.setApproverType(approverType);
        specialistEntity.setStatus(SpecialistStatus.APPROVED);
        repository.save(specialistEntity);
    }

    @Transactional
    @Override
    public void manage(UUID id, UUID ownerId) {
        SpecialistStatus status = repository.findStatusById(id).orElseThrow(
                SpecialistStatusNotFoundByIdException::new
        );
        if (!status.equals(SpecialistStatus.APPROVED)) {
            throw new UnableSpecialistManageException();
        }
        repository.updateStatusAndOwnerIdById(id, ownerId, SpecialistStatus.MANAGED);
    }

    @Transactional
    @Override
    public void recall(UUID id) {
        SpecialistStatus status = repository.findStatusById(id).orElseThrow(
                SpecialistStatusNotFoundByIdException::new
        );
        if (!status.equals(SpecialistStatus.APPROVED)) {
            throw new UnableSpecialistRecallException();
        }
        repository.updateStatusById(id, SpecialistStatus.RECALLED);
    }
}
