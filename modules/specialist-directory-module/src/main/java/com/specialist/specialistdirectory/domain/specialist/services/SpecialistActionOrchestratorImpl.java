package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.ActionEntity;
import com.specialist.specialistdirectory.domain.specialist.models.SpecialistActionEvent;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.ContactDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.enums.ActionType;
import com.specialist.specialistdirectory.domain.specialist.models.enums.ContactType;
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

    // TODO create topic
    @Value("${api.kafka.topic.specialist-action}")
    public String TOPIC;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void recallRequest(UUID id, ContactType contactType) {
        ActionEntity actionEntity = new ActionEntity(ActionType.RECALL, id, null, 300L);
        requestHandle(actionEntity, contactType);
    }

    @Override
    public void recall(String code) {
        ActionEntity actionEntity = codeHandle(code);
        // DISCUSS schedule delete
        specialistStatusService.recall(actionEntity.getSpecialistId());
    }

    @Override
    public void manageRequest(UUID id, UUID accountId, ContactType contactType) {
        ActionEntity actionEntity = new ActionEntity(ActionType.MANAGE, id, accountId, 600L);
        requestHandle(actionEntity, contactType);
    }

    @Override
    public void manage(String code) {
        ActionEntity actionEntity = codeHandle(code);
        specialistStatusService.manage(actionEntity.getSpecialistId(), actionEntity.getAccountId());
    }

    private void requestHandle(ActionEntity actionEntity, ContactType contactType) {
        String code = CodeGenerator.generate();
        SpecialistResponseDto specialistDto = specialistService.findById(actionEntity.getSpecialistId());
        ContactDto contactDto = specialistDto.getContacts().stream()
                .filter(contact -> contact.type().equals(contactType))
                .findFirst()
                .orElseThrow(NoSuchSpecialistContactException::new);
        actionEntity.setCode(code);
        actionRepository.save(actionEntity);
        kafkaTemplate.send(
                TOPIC,
                new SpecialistActionEvent(
                        actionEntity.getType(),
                        contactDto.value(),
                        contactDto.type(),
                        code)
        );
    }

    private ActionEntity codeHandle(String code) {
        ActionEntity actionEntity = actionRepository.findById(code).orElse(null);
        if (actionEntity == null) {
            throw new CodeExpiredException();
        }
        actionRepository.deleteById(code);
        return actionEntity;
    }
}
