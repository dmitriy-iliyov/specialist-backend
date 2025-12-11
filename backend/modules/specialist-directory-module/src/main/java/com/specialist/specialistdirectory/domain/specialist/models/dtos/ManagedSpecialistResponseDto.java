package com.specialist.specialistdirectory.domain.specialist.models.dtos;

import com.specialist.specialistdirectory.domain.specialist.models.enums.Gender;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistLanguage;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistState;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;

import java.util.List;
import java.util.UUID;

public class ManagedSpecialistResponseDto extends SpecialistResponseDto {

    private final String avatarUrl;

    public ManagedSpecialistResponseDto(UUID id, UUID ownerId, String fullName, Gender gender, String typeTitle,
                                        String anotherType, Integer experience, List<SpecialistLanguage> languages,
                                        String details, String cityTitle, String cityCode, String address,
                                        List<ContactDto> contacts, String site, SpecialistStatus status,
                                        SpecialistState state, double totalRating, long reviewsCount, String avatarUrl) {
        super(id, ownerId, fullName, gender, typeTitle, anotherType, experience, languages, details, cityTitle, cityCode,
                address, contacts, site, status, state, totalRating, reviewsCount);
        this.avatarUrl = avatarUrl;
    }
}
