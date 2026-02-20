package com.specialist.specialistdirectory.ut.domain.specialist.services;

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
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistActionFacadeImpl;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistService;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistStateService;
import com.specialist.specialistdirectory.exceptions.CodeExpiredException;
import com.specialist.specialistdirectory.exceptions.NoSuchSpecialistContactException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialistActionFacadeImplUnitTests {

    @Mock
    private SpecialistService specialistService;
    @Mock
    private SpecialistStateService specialistStateService;
    @Mock
    private SpecialistActionRepository actionRepository;
    @Mock
    private SystemSpecialistProfileService specialistProfileService;
    @Mock
    private SystemAccountDemoteFacade accountDemoteService;
    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private SpecialistActionFacadeImpl facade;

    @Test
    @DisplayName("UT: recallRequest() should save action and publish event")
    void recallRequest_shouldSaveAndPublish() {
        UUID specialistId = UUID.randomUUID();
        ContactDto contact = new ContactDto(ContactType.EMAIL, "test@mail.com");
        SpecialistResponseDto specialistDto = mock(SpecialistResponseDto.class);
        when(specialistDto.getContacts()).thenReturn(List.of(contact));

        when(specialistService.findById(specialistId)).thenReturn(specialistDto);

        facade.recallRequest(specialistId, ContactType.EMAIL);

        verify(actionRepository).save(any(SpecialistActionEntity.class));
        verify(eventPublisher).publishEvent(any(SpecialistActionEvent.class));
    }

    @Test
    @DisplayName("UT: recallRequest() when no contact should throw exception")
    void recallRequest_noContact_shouldThrow() {
        UUID specialistId = UUID.randomUUID();
        SpecialistResponseDto specialistDto = mock(SpecialistResponseDto.class);
        when(specialistDto.getContacts()).thenReturn(List.of());

        when(specialistService.findById(specialistId)).thenReturn(specialistDto);

        assertThrows(NoSuchSpecialistContactException.class, () -> facade.recallRequest(specialistId, ContactType.EMAIL));
    }

    @Test
    @DisplayName("UT: recall() when code valid should recall state")
    void recall_validCode_shouldRecall() {
        String code = "code";
        SpecialistActionEntity action = new SpecialistActionEntity(ActionType.RECALL, UUID.randomUUID(), null, 300L);

        when(actionRepository.findById(code)).thenReturn(Optional.of(action));

        facade.recall(code);

        verify(actionRepository).deleteById(code);
        verify(specialistStateService).recall(action.getSpecialistId());
    }

    @Test
    @DisplayName("UT: recall() when code invalid should throw exception")
    void recall_invalidCode_shouldThrow() {
        String code = "code";
        when(actionRepository.findById(code)).thenReturn(Optional.empty());
        assertThrows(CodeExpiredException.class, () -> facade.recall(code));
    }

    @Test
    @DisplayName("UT: manage() when code valid should manage state and demote account")
    void manage_validCode_shouldManageAndDemote() {
        String code = "code";
        UUID accountId = UUID.randomUUID();
        SpecialistActionEntity action = new SpecialistActionEntity(ActionType.MANAGE, UUID.randomUUID(), accountId, 600L);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(actionRepository.findById(code)).thenReturn(Optional.of(action));

        facade.manage(accountId, code, request, response);

        verify(actionRepository).deleteById(code);
        verify(specialistStateService).manage(action.getSpecialistId(), accountId);
        verify(specialistProfileService).setSpecialistCardId(action.getSpecialistId());
        verify(accountDemoteService).demote(any(DemoteRequest.class));
    }
}
