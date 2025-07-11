package com.aidcompass.aggregator.api.appointment.dto;

import com.aidcompass.users.doctor.models.dto.PublicDoctorResponseDto;
import com.aidcompass.users.doctor.specialization.models.DoctorSpecialization;
import com.aidcompass.users.jurist.models.dto.PublicJuristResponseDto;
import com.aidcompass.users.jurist.specialization.models.JuristSpecialization;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
)
public interface DtoMapper {

    @Mapping(target = "specializations", source = "specializations", qualifiedByName = "translate")
    PublicVolunteerDto toVolunteerDto(PublicDoctorResponseDto dto);

    @Mapping(target = "specializations", source = "specializations", qualifiedByName = "translateJurist")
    PublicVolunteerDto toVolunteerDto(PublicJuristResponseDto dto);

    @Named("translate")
    default List<String> translate(List<DoctorSpecialization> specializations) {
        List<String> translated = new ArrayList<>();
        for (DoctorSpecialization specialization : specializations) {
            translated.add(specialization.getTranslate());
        }
        return translated;
    }

    @Named("translateJurist")
    default List<String> translateJurist(List<JuristSpecialization> specializations) {
        List<String> translated = new ArrayList<>();
        for (JuristSpecialization specialization : specializations) {
            translated.add(specialization.getTranslate());
        }
        return translated;
    }
}
