package com.aidcompass.users.jurist.specialization.mapper;

import com.aidcompass.users.jurist.specialization.models.JuristSpecialization;
import com.aidcompass.users.jurist.specialization.models.JuristSpecializationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface JuristSpecializationMapper {

    JuristSpecializationEntity toEntity(JuristSpecialization specialization);

    List<JuristSpecializationEntity> toEntityList(List<JuristSpecialization> specializations);

    default JuristSpecialization toEnum(JuristSpecializationEntity entity) {
        return entity == null ? null : entity.getSpecialization();
    }

    List<JuristSpecialization> toEnumList(List<JuristSpecialization> entityList);
}
