package com.specialist.specialistdirectory.domain.specialist.models.dtos;

import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistLanguage;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistState;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class FullSpecialistResponseDto extends SpecialistResponseDto {

    private final SpecialistInfoResponseDto info;

    public FullSpecialistResponseDto(UUID id, UUID creatorId, String fullName, String typeTitle, String anotherType,
                                     List<SpecialistLanguage> languages, String cityTitle, String cityCode,
                                     String address, List<ContactDto> contacts, String site, SpecialistStatus status,
                                     SpecialistState state, double totalRating, long reviewsCount,
                                     SpecialistInfoResponseDto info) {
        super(id, creatorId, fullName, typeTitle, anotherType, languages, cityTitle, cityCode, address, contacts, site,
                status, state, totalRating, reviewsCount);
        this.info = info;
    }
}
