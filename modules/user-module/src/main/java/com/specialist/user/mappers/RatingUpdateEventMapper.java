package com.specialist.user.mappers;

import com.specialist.contracts.user.CreatorRatingUpdateEvent;
import com.specialist.user.models.RatingUpdateEventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RatingUpdateEventMapper {
    RatingUpdateEventEntity toEntity(CreatorRatingUpdateEvent event);
}
