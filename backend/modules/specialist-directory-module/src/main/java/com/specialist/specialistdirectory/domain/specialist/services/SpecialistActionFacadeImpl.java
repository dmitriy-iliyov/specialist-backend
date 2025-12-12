package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.contracts.auth.DemoteRequest;
import com.specialist.contracts.auth.SystemAccountDemoteFacade;
import com.specialist.contracts.profile.SystemSpecialistProfileService;
import com.specialist.contracts.specialistdirectory.dto.ActionType;
import com.specialist.contracts.specialistdirectory.dto.ContactType;
import com.specialist.contracts.specialistdirectory.dto.SpecialistActionEvent;
import com.specialist.specialistdirectory.domain.specialist.models.SpecialistActionEntity;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.ContactDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.repositories.SpecialistActionRepository;
import com.specialist.specialistdirectory.exceptions.CodeExpiredException;
import com.specialist.specialistdirectory.exceptions.NoSuchSpecialistContactException;
import com.specialist.utils.CodeGenerator;
import io.github.dmitriyiliyov.springoutbox.publisher.aop.OutboxPublish;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecialistActionFacadeImpl implements SpecialistActionFacade {

    private final SpecialistService specialistService;
    private final SpecialistStateService specialistStateService;
    private final SpecialistActionRepository actionRepository;
    private final SystemSpecialistProfileService specialistProfileService;
    private final SystemAccountDemoteFacade accountDemoteService;

    // TODO outbox & scheduler
    @Value("${api.kafka.topic.specialists-action}")
    public String TOPIC;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    @Override
    public void recallRequest(UUID id, ContactType contactType) {
        SpecialistActionEntity specialistActionEntity = new SpecialistActionEntity(ActionType.RECALL, id, null, 300L);
        requestHandle(specialistActionEntity, contactType);
    }

    @Override
    public void recall(String code) {
        SpecialistActionEntity specialistActionEntity = codeHandle(code);
        // DISCUSS schedule delete but cache should immediately invalidate
        specialistStateService.recall(specialistActionEntity.getSpecialistId());
    }

    @Transactional
    @Override
    public void manageRequest(UUID id, UUID accountId, ContactType contactType) {
        SpecialistActionEntity specialistActionEntity = new SpecialistActionEntity(ActionType.MANAGE, id, accountId, 600L);
        requestHandle(specialistActionEntity, contactType);
    }

    @Transactional
    @Override
    public void manage(UUID accountId, String code, HttpServletRequest request, HttpServletResponse response) {
        SpecialistActionEntity specialistActionEntity = codeHandle(code);
        specialistStateService.manage(specialistActionEntity.getSpecialistId(), specialistActionEntity.getAccountId());
        specialistProfileService.setSpecialistCardId(specialistActionEntity.getSpecialistId());
        accountDemoteService.demote(new DemoteRequest(accountId, Set.of("SPECIALIST_CREATE"), request, response));
    }

    @OutboxPublish(eventType = "validate-specialist-action")
    private SpecialistActionEvent requestHandle(SpecialistActionEntity specialistActionEntity, ContactType contactType) {
        String code = CodeGenerator.generate();
        SpecialistResponseDto specialistDto = specialistService.findById(specialistActionEntity.getSpecialistId());
        ContactDto contactDto = specialistDto.getContacts().stream()
                .filter(contact -> contact.type().equals(contactType))
                .findFirst()
                .orElseThrow(NoSuchSpecialistContactException::new);
        specialistActionEntity.setCode(code);
        actionRepository.save(specialistActionEntity);
        return new SpecialistActionEvent(
                specialistActionEntity.getType(),
                contactDto.value(),
                contactDto.type(),
                code
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
