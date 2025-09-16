package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.contracts.user.SystemSpecialistProfileService;
import com.specialist.specialistdirectory.domain.specialist.models.SpecialistActionEntity;
import com.specialist.contracts.specialistdirectory.dto.SpecialistActionEvent;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.ContactDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.contracts.specialistdirectory.dto.ActionType;
import com.specialist.contracts.specialistdirectory.dto.ContactType;
import com.specialist.specialistdirectory.domain.specialist.repositories.SpecialistActionRepository;
import com.specialist.specialistdirectory.exceptions.CodeExpiredException;
import com.specialist.specialistdirectory.exceptions.NoSuchSpecialistContactException;
import com.specialist.utils.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecialistActionOrchestratorImpl implements SpecialistActionOrchestrator {

    private final SpecialistService specialistService;
    private final SpecialistStatusService specialistStatusService;
    private final SpecialistActionRepository actionRepository;
    private final SystemSpecialistProfileService specialistProfileService;

    // TODO create topic
    @Value("${api.kafka.topic.specialist-action}")
    public String TOPIC;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void recallRequest(UUID id, ContactType contactType) {
        SpecialistActionEntity specialistActionEntity = new SpecialistActionEntity(ActionType.RECALL, id, null, 300L);
        requestHandle(specialistActionEntity, contactType);
    }

    @Override
    public void recall(String code) {
        SpecialistActionEntity specialistActionEntity = codeHandle(code);
        // DISCUSS schedule delete
        specialistStatusService.recall(specialistActionEntity.getSpecialistId());
    }

    @Override
    public void manageRequest(UUID id, UUID accountId, ContactType contactType) {
        SpecialistActionEntity specialistActionEntity = new SpecialistActionEntity(ActionType.MANAGE, id, accountId, 600L);
        requestHandle(specialistActionEntity, contactType);
    }

    @Override
    public void manage(String code) {
        SpecialistActionEntity specialistActionEntity = codeHandle(code);
        specialistStatusService.manage(specialistActionEntity.getSpecialistId(), specialistActionEntity.getAccountId());
        specialistProfileService.setSpecialistCardId(specialistActionEntity.getSpecialistId());
    }

    private void requestHandle(SpecialistActionEntity specialistActionEntity, ContactType contactType) {
        String code = CodeGenerator.generate();
        SpecialistResponseDto specialistDto = specialistService.findById(specialistActionEntity.getSpecialistId());
        ContactDto contactDto = specialistDto.getContacts().stream()
                .filter(contact -> contact.type().equals(contactType))
                .findFirst()
                .orElseThrow(NoSuchSpecialistContactException::new);
        specialistActionEntity.setCode(code);
        actionRepository.save(specialistActionEntity);
        kafkaTemplate.send(
                TOPIC,
                new SpecialistActionEvent(
                        specialistActionEntity.getType(),
                        contactDto.value(),
                        contactDto.type(),
                        code)
        );
    }

    private SpecialistActionEntity codeHandle(String code) {
        SpecialistActionEntity specialistActionEntity = actionRepository.findById(code).orElse(null);
        if (specialistActionEntity == null) {
            throw new CodeExpiredException();
        }
        actionRepository.deleteById(code);
        return specialistActionEntity;
    }
}
