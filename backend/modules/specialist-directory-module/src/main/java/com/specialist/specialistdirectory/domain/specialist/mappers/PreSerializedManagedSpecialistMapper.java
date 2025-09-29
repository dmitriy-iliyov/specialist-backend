package com.specialist.specialistdirectory.domain.specialist.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.specialist.contracts.specialistdirectory.dto.ManagedSpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PreSerializedManagedSpecialistMapper implements ManagedSpecialistMapper {

    private final ObjectMapper mapper;

    @Override
    public ManagedSpecialistResponseDto toManagedDto(SpecialistResponseDto dto) {
        try {
            String jsonLanguages = mapper.writeValueAsString(dto.getLanguages());
            String jsonContacts = mapper.writeValueAsString(dto.getContacts());
            return new ManagedSpecialistResponseDto(
                    dto.getId(),
                    dto.getOwnerId(),
                    dto.getFullName(),
                    dto.getTypeTitle(),
                    dto.getAnotherType(),
                    jsonLanguages,
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
    public List<ManagedSpecialistResponseDto> toManagedDtoList(List<SpecialistResponseDto> dtoList) {
        return dtoList.stream()
                .map(this::toManagedDto)
                .toList();
    }
}
