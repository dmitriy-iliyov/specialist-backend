package com.aidcompass.users.doctor.mapper;

import com.aidcompass.users.detail.mapper.DetailMapper;
import com.aidcompass.users.doctor.models.DoctorEntity;
import com.aidcompass.users.doctor.models.dto.DoctorDto;
import com.aidcompass.users.doctor.models.dto.PrivateDoctorResponseDto;
import com.aidcompass.users.doctor.models.dto.PublicDoctorResponseDto;
import com.aidcompass.users.doctor.specialization.mapper.DoctorSpecializationMapper;
import com.aidcompass.users.doctor.specialization.models.DoctorSpecialization;
import com.aidcompass.users.doctor.specialization.models.DoctorSpecializationEntity;
import com.aidcompass.core.general.contracts.dto.BaseSystemVolunteerDto;
import com.aidcompass.users.general.dto.BasePrivateVolunteerDto;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {DoctorSpecializationMapper.class, DetailMapper.class}
)
public interface DoctorMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "specializations", source = "specializationEntityList")
    @Mapping(target = "gender", expression = "java(com.aidcompass.users.gender.Gender.toEnum(dto.getGender()))")
    DoctorEntity toEntity(UUID id, List<DoctorSpecializationEntity> specializationEntityList, DoctorDto dto);

    @Mapping(target = "isApproved",  source = "approved")
    @Mapping(target = "profileStatus", source = "profileStatusEntity.profileStatus")
    @Mapping(target = "profileProgress",
            expression = "java((int) (100 * ((double) entity.getProfileProgress() / com.aidcompass.users.profile_status.ProfileConfig.MAX_PROFILE_PROGRESS)))")
    BasePrivateVolunteerDto toBaseDto(DoctorEntity entity);

    @Mapping(target = "baseDto", expression = "java(toBaseDto(entity))")
    @Mapping(target = "specializations", source = "specializations")
    PrivateDoctorResponseDto toPrivateDto(DoctorEntity entity);

    @Named("toPrivateDtoWithParam")
    @Mapping(target = "baseDto", expression = "java(toBaseDto(entity))")
    @Mapping(target = "specializations", source = "paramSpecializations")
    PrivateDoctorResponseDto toPrivateDto(List<DoctorSpecialization> paramSpecializations, DoctorEntity entity);

    @Mapping(target = "specializations", source = "specializations")
    PublicDoctorResponseDto toPublicDto(DoctorEntity entity);

    @Mapping(target = "specializations", source = "paramSpecializations")
    PublicDoctorResponseDto toPublicDto(List<DoctorSpecialization> paramSpecializations, DoctorEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "specializations", source = "specializationEntityList")
    @Mapping(target = "gender", expression = "java(com.aidcompass.users.gender.Gender.toEnum(dto.getGender()))")
    void updateEntityFromUpdateDto(List<DoctorSpecializationEntity> specializationEntityList, DoctorDto dto,
                                   @MappingTarget DoctorEntity entity);

    BaseSystemVolunteerDto toSystemDto(DoctorEntity doctorEntity);
}
