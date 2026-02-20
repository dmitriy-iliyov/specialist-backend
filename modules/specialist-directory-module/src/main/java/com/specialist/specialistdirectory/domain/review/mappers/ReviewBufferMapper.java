package com.specialist.specialistdirectory.domain.review.mappers;

import com.specialist.contracts.profile.CreatorRatingUpdateEvent;
import com.specialist.specialistdirectory.domain.review.models.CreatorRatingBufferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewBufferMapper {
    CreatorRatingUpdateEvent toEvent(CreatorRatingBufferEntity entity);
    List<CreatorRatingUpdateEvent> toEventList(List<CreatorRatingBufferEntity> entityList);
}