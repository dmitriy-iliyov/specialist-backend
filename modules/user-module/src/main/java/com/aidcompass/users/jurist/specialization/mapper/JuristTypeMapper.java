package com.aidcompass.users.jurist.specialization.mapper;

import com.aidcompass.users.jurist.specialization.models.JuristType;
import com.aidcompass.users.jurist.specialization.models.JuristTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface JuristTypeMapper {

    JuristTypeEntity toEntity(JuristType type);

    List<JuristTypeEntity> toEntityList(List<JuristType> typeList);

    default JuristType toEnum(JuristTypeEntity entity) {
        return entity == null ? null : entity.getType();
    }
}
