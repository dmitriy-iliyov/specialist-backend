package com.specialist.specialistdirectory.domain.specialist.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.specialist.contracts.specialistdirectory.dto.ExternalManagedSpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.ManagedSpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PreSerializedManagedSpecialistMapper implements ManagedSpecialistMapper {

    private final ObjectMapper mapper;

    @Override
    public ManagedSpecialistResponseDto toManagedDto(SpecialistResponseDto dto, String avatarUrl) {
        return new ManagedSpecialistResponseDto(
                dto.getId(),
                dto.getOwnerId(),
                dto.getFullName(),
                dto.getGender(),
                dto.getTypeTitle(),
                dto.getAnotherType(),
                dto.getExperience(),
                dto.getLanguages(),
                dto.getDetails(),
                dto.getCityTitle(),
                dto.getCityCode(),
                dto.getAddress(),
                dto.getContacts(),
                dto.getSite(),
                dto.getStatus(),
                dto.getState(),
                dto.getTotalRating(),
                dto.getReviewsCount(),
                avatarUrl
        );
    }

    @Override
    public ExternalManagedSpecialistResponseDto toExternalManagedDto(SpecialistResponseDto dto) {
        try {
            String jsonLanguages = mapper.writeValueAsString(dto.getLanguages());
            String jsonContacts = mapper.writeValueAsString(dto.getContacts());
            return new ExternalManagedSpecialistResponseDto(
                    dto.getId(),
                    dto.getOwnerId(),
                    dto.getFullName(),
                    dto.getGender().toJson(),
                    dto.getTypeTitle(),
                    dto.getAnotherType(),
                    dto.getExperience(),
                    jsonLanguages,
                    dto.getDetails(),
                    dto.getCityTitle(),
                    dto.getCityCode(),
                    dto.getAddress(),
                    jsonContacts,
                    dto.getSite(),
                    dto.getStatus().toString(),
                    dto.getTotalRating(),
                    dto.getReviewsCount()
            );
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to serialize to JSON", e);
        }
    }

    @Override
    public List<ExternalManagedSpecialistResponseDto> toExternalManagedDtoList(List<SpecialistResponseDto> dtoList) {
        return dtoList.stream()
                .map(this::toExternalManagedDto)
                .toList();
    }
}
