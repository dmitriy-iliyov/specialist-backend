package com.aidcompass.aggregator.api.doctor;

import com.aidcompass.aggregator.AggregatorUtils;
import com.aidcompass.aggregator.api.doctor.dto.DoctorCardDto;
import com.aidcompass.aggregator.api.doctor.dto.DoctorPublicProfileDto;
import com.aidcompass.aggregator.api.doctor.dto.DoctorPrivateProfileDto;
import com.aidcompass.core.contact.core.models.dto.PrivateContactResponseDto;
import com.aidcompass.users.doctor.models.dto.FullPrivateDoctorResponseDto;
import com.aidcompass.users.doctor.models.dto.FullPublicDoctorResponseDto;
import com.aidcompass.users.doctor.models.dto.PublicDoctorResponseDto;
import com.aidcompass.users.doctor.services.DoctorService;
import com.aidcompass.users.doctor.specialization.models.DoctorSpecialization;
import com.aidcompass.users.gender.Gender;
import com.aidcompass.core.general.contracts.dto.PageResponse;
import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.schedule.interval.models.dto.NearestIntervalDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DoctorAggregatorService {

    private final DoctorService doctorService;
    private final AggregatorUtils utils;


    public DoctorPublicProfileDto findPublicProfile(UUID id) {
        String url = utils.findAvatarUrlByOwnerId(id);
        FullPublicDoctorResponseDto fullDto = doctorService.findFullPublicById(id);
        Long appointmentDuration = utils.findDurationByOwnerId(id);
        return new DoctorPublicProfileDto(url, fullDto, utils.findAllContactByOwnerId(id), appointmentDuration);
    }

    public DoctorPrivateProfileDto findPrivateProfile(UUID id) {
        String url = utils.findAvatarUrlByOwnerId(id);
        FullPrivateDoctorResponseDto fullDto = doctorService.findFullPrivateById(id);
        Long appointmentDuration = utils.findDurationByOwnerId(id);
        return new DoctorPrivateProfileDto(url, fullDto, utils.findAllPrivateContactByOwnerId(id), appointmentDuration);
    }

    public PageResponse<DoctorCardDto> findAllApproved(int page, int size) {
        PageResponse<PublicDoctorResponseDto> pageResponse = doctorService.findAllApproved(page, size);
        return new PageResponse<>(
                this.aggregate(pageResponse.data()),
                pageResponse.totalPage()
        );
    }

    public PageResponse<DoctorPrivateProfileDto> findAllUnapproved(int page, int size) {
        PageResponse<FullPrivateDoctorResponseDto> doctors = doctorService.findAllUnapproved(page, size);
        return new PageResponse<>(
                this.aggregateToPrivate(doctors.data()),
                doctors.totalPage()
        );
    }

    public PageResponse<DoctorPrivateProfileDto> findAllUnapprovedByNamesCombination(String firstName, String secondName,
                                                                                     String lastName,int page, int size) {
        PageResponse<FullPrivateDoctorResponseDto> doctors = doctorService.findAllUnapprovedByNamesCombination(firstName, secondName, lastName, page, size);
        return new PageResponse<>(
                this.aggregateToPrivate(doctors.data()),
                doctors.totalPage()
        );
    }

    public PageResponse<DoctorCardDto> findAllApprovedBySpecialization(DoctorSpecialization doctorSpecialization, int page, int size) {
        PageResponse<PublicDoctorResponseDto> pageResponse = doctorService.findAllBySpecialization(doctorSpecialization, page, size);
        return new PageResponse<>(
                this.aggregate(pageResponse.data()),
                pageResponse.totalPage()
        );
    }

    public PageResponse<DoctorCardDto> findAllByNamesCombination(String firstName, String secondName, String lastName, int page, int size) {
        PageResponse<PublicDoctorResponseDto> pageResponse = doctorService.findAllByNamesCombination(firstName, secondName, lastName, page, size);
        return new PageResponse<>(
                this.aggregate(pageResponse.data()),
                pageResponse.totalPage()
        );
    }

    public PageResponse<DoctorCardDto> findAllByGender(Gender gender, int page, int size) {
        PageResponse<PublicDoctorResponseDto> pageResponse = doctorService.findAllByGender(gender, page, size);
        return new PageResponse<>(
                aggregate(pageResponse.data()),
                pageResponse.totalPage()
        );
    }

    @Transactional(noRollbackFor = BaseNotFoundException.class)
    public void delete(UUID id, String password, HttpServletRequest request, HttpServletResponse response) {
        utils.deleteAllUserAlignments(id, password, request, response);
        utils.deleteAllVolunteerAlignments(id);
        doctorService.deleteById(id);
    }

    private List<DoctorCardDto> aggregate(List<PublicDoctorResponseDto> dtoList) {
        List<DoctorCardDto> doctorCardDtoList = new ArrayList<>();

        Set<UUID> uuids = new HashSet<>();
        for (PublicDoctorResponseDto dto : dtoList) {
            uuids.add(dto.id());
        }
        Map<UUID, String> avatarUrls = utils.findAllAvatarUrlByOwnerIdIn(uuids);
        Map<UUID, NearestIntervalDto> nearestIntervalDtoList = utils.findAllNearestByOwnerIdIn(uuids);
        Map<UUID, Long> durations = utils.findAllDurationByOwnerIdIn(uuids);

        for (PublicDoctorResponseDto dto : dtoList) {
            doctorCardDtoList.add(
                    new DoctorCardDto(
                            avatarUrls.get(dto.id()),
                            dto,
                            nearestIntervalDtoList.get(dto.id()),
                            durations.get(dto.id()))
            );
        }
        return doctorCardDtoList;
    }

    private List<DoctorPrivateProfileDto> aggregateToPrivate(List<FullPrivateDoctorResponseDto> dtoList) {
        List<DoctorPrivateProfileDto> systemCardDtos = new ArrayList<>();
        Set<UUID> uuids = new HashSet<>();
        for (FullPrivateDoctorResponseDto dto : dtoList) {
            uuids.add(dto.doctor().baseDto().id());
        }
        Map<UUID, String> avatarUrls = utils.findAllAvatarUrlByOwnerIdIn(uuids);
        Map<UUID, Long> durations = utils.findAllDurationByOwnerIdIn(uuids);
        Map<UUID, List<PrivateContactResponseDto>> contacts = utils.findAllPrivateContactByOwnerIdIn(uuids);

        for (FullPrivateDoctorResponseDto dto : dtoList) {
            UUID id = dto.doctor().baseDto().id();
            systemCardDtos.add(
                    new DoctorPrivateProfileDto(
                            avatarUrls.get(id),
                            dto,
                            contacts.get(id),
                            durations.get(id))
            );
        }

        return systemCardDtos;
    }
}