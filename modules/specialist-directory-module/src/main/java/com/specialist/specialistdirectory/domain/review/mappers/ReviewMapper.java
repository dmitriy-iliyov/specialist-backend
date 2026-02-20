package com.specialist.specialistdirectory.domain.review.mappers;

import com.specialist.specialistdirectory.domain.review.models.ReviewEntity;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewCreateDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewUpdateDto;
import com.specialist.utils.InstantToLocalDataTimeConverter;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {InstantToLocalDataTimeConverter.class, ReviewPictureUrlResolver.class}
)
public interface ReviewMapper {

    ReviewEntity toEntity(ReviewCreateDto dto);

    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "pictureUrl", source = "pictureUrl", qualifiedByName = "resolvePictureUrl")
    ReviewResponseDto toDto(ReviewEntity entity);

    List<ReviewResponseDto> toDtoList(List<ReviewEntity> entityList);

    void updateEntityFromDto(ReviewUpdateDto dto, @MappingTarget ReviewEntity entity);
}
