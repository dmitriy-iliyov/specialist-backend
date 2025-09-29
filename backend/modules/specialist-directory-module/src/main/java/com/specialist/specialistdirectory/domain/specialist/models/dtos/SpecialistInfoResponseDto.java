package com.specialist.specialistdirectory.domain.specialist.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.specialist.specialistdirectory.domain.specialist.models.enums.ApproverType;
import com.specialist.specialistdirectory.domain.specialist.models.enums.CreatorType;

import java.util.UUID;

public record SpecialistInfoResponseDto(
        UUID id,
        @JsonProperty("creator_type")
        CreatorType creatorType,
        @JsonProperty("approver_id")
        UUID approverId,
        @JsonProperty("approver_type")
        ApproverType approverType
) { }
