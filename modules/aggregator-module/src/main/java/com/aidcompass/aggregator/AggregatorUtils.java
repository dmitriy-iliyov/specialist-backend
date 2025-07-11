package com.aidcompass.aggregator;

import com.aidcompass.schedule.appointment.services.AppointmentService;
import com.aidcompass.schedule.appointment_duration.AppointmentDurationService;
import com.aidcompass.avatar.AvatarService;
import com.aidcompass.core.contact.core.models.dto.PrivateContactResponseDto;
import com.aidcompass.core.contact.core.models.dto.PublicContactResponseDto;
import com.aidcompass.core.contact.core.models.dto.system.SystemContactDto;
import com.aidcompass.core.contact.core.services.ContactService;
import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.schedule.interval.models.dto.NearestIntervalDto;
import com.aidcompass.schedule.interval.services.IntervalService;
import com.aidcompass.schedule.interval.services.NearestIntervalService;
import com.aidcompass.core.security.domain.user.services.UserOrchestrator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AggregatorUtils {

    private final AvatarService avatarService;
    private final NearestIntervalService nearestIntervalService;
    private final AppointmentDurationService durationService;
    private final ContactService contactService;
    private final UserOrchestrator userOrchestrator;
    private final IntervalService intervalService;
    private final AppointmentService appointmentService;


    public String findAvatarUrlByOwnerId(UUID id) {
        try {
            return avatarService.findUrlByUserId(id);
        } catch (BaseNotFoundException e) {
            return null;
        }
    }

    public Map<UUID, String> findAllAvatarUrlByOwnerIdIn(Set<UUID> idList) {
        return avatarService.findAllUrlByOwnerIdIn(idList);
    }

    public Map<UUID, NearestIntervalDto> findAllNearestByOwnerIdIn(Set<UUID> idList) {
        return nearestIntervalService.findAll(idList);
    }

    public Long findDurationByOwnerId(UUID id) {
        try {
            return durationService.findByOwnerId(id);
        } catch (BaseNotFoundException e) {
            return null;
        }
    }

    public Map<UUID, Long> findAllDurationByOwnerIdIn(Set<UUID> ownerIds) {
        return durationService.findAllByOwnerIdIn(ownerIds);
    }

    public List<PublicContactResponseDto> findAllContactByOwnerId(UUID id) {
        return contactService.findAllPublicByOwnerId(id);
    }

    public Map<UUID, SystemContactDto> findPrimaryContactByOwnerIdIn(Set<UUID> ids) {
        List<SystemContactDto> dtoList = contactService.findAllPrimaryByOwnerIdIn(ids);
        return dtoList.stream().collect(Collectors.toMap(SystemContactDto::getOwnerId, Function.identity()));
    }

    public Map<UUID, List<PrivateContactResponseDto>> findAllPrivateContactByOwnerIdIn(Set<UUID> ids) {
        return contactService.findAllPrivateContactByOwnerIdIn(ids);
    }

    public List<PrivateContactResponseDto> findAllPrivateContactByOwnerId(UUID id) {
        return contactService.findAllPrivateByOwnerId(id);
    }

    public void deleteAllUserAlignments(UUID id, String password, HttpServletRequest request, HttpServletResponse response) {
        try {
            avatarService.delete(id);
        } catch (BaseNotFoundException ignore) { }
        contactService.deleteAll(id);
        appointmentService.deleteAll(id);
        userOrchestrator.deleteByPassword(id, password, request, response);
    }

    public void deleteAllVolunteerAlignments(UUID id) {
        durationService.deleteByOwnerId(id);
        intervalService.deleteAllByOwnerId(id);
    }
}