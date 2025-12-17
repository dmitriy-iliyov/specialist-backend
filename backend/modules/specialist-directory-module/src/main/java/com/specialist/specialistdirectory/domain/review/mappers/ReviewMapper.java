package com.specialist.specialistdirectory.domain.review.mappers;

import com.specialist.picture.PictureStorage;
import com.specialist.specialistdirectory.domain.review.models.ReviewEntity;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewCreateDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewUpdateDto;
import com.specialist.utils.InstantToLocalDataTimeConverter;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {InstantToLocalDataTimeConverter.class})
public abstract class ReviewMapper {

    @Qualifier("reviewPictureStorage")
    protected PictureStorage pictureStorage;

    @Named("resolvePictureUrl")
    protected String resolvePictureUrl(String avatarUrl) {
        return pictureStorage.resolvePictureUrl(avatarUrl);
    }

    public abstract ReviewEntity toEntity(ReviewCreateDto dto);

    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "pictureUrl", source = "pictureUrl", qualifiedByName = "resolvePictureUrl")
    public abstract ReviewResponseDto toDto(ReviewEntity entity);

    public abstract List<ReviewResponseDto> toDtoList(List<ReviewEntity> entityList);

    public abstract void updateEntityFromDto(ReviewUpdateDto dto, @MappingTarget ReviewEntity entity);
}
