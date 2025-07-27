package com.aidcompass.specialistdirectory.domain.review.mappers;

import com.aidcompass.contracts.user.CreatorRatingUpdateEvent;
import com.aidcompass.specialistdirectory.domain.review.models.ReviewBufferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewBufferMapper {
    CreatorRatingUpdateEvent toEvent(ReviewBufferEntity entity);
    List<CreatorRatingUpdateEvent> toEventList(List<ReviewBufferEntity> entityList);
}