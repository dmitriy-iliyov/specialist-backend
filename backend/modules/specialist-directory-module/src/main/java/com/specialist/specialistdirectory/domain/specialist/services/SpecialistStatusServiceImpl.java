package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.specialist.specialistdirectory.domain.specialist.models.SpecialistInfoEntity;
import com.specialist.specialistdirectory.domain.specialist.models.enums.ApproverType;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import com.specialist.specialistdirectory.domain.specialist.repositories.SpecialistRepository;
import com.specialist.specialistdirectory.exceptions.SpecialistNotFoundByIdException;
import com.specialist.specialistdirectory.exceptions.UnableSpecialistApproveException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecialistStatusServiceImpl implements SpecialistStatusService {

    private final SpecialistRepository repository;

    @Transactional
    @Override
    public void approve(UUID id, UUID approverId, ApproverType approverType) {
        SpecialistEntity specialistEntity = repository.findWithInfoById(id).orElseThrow(SpecialistNotFoundByIdException::new);
        if (!specialistEntity.getStatus().equals(SpecialistStatus.UNAPPROVED)) {
            throw new UnableSpecialistApproveException();
        }
        SpecialistInfoEntity infoEntity = specialistEntity.getInfo();
        infoEntity.setApproverId(approverId);
        infoEntity.setApproverType(approverType);
        specialistEntity.setStatus(SpecialistStatus.APPROVED);
        repository.save(specialistEntity);
    }

    @Transactional
    @Override
    public void suspend(UUID id) {
        repository.updateStatusById(id, SpecialistStatus.SUSPENDED);
    }
}
