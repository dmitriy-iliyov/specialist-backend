package com.aidcompass.specialistdirectory.domain.review;

import com.aidcompass.specialistdirectory.domain.review.models.ReviewEntity;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewCreateDto;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewUpdateDto;
import com.aidcompass.specialistdirectory.utils.converters.InstantToLocalDataTimeConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {InstantToLocalDataTimeConverter.class})
public interface ReviewMapper {

    ReviewEntity toEntity(ReviewCreateDto dto);

    @Mapping(target = "createdAt", source = "createdAt")
    ReviewResponseDto toDto(ReviewEntity entity);

    List<ReviewResponseDto> toDtoList(List<ReviewEntity> entityList);

    void updateEntityFromDto(ReviewUpdateDto dto, @MappingTarget ReviewEntity entity);
}
