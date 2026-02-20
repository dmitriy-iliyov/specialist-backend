package com.specialist.profile.mappers;

import com.specialist.contracts.profile.CreatorRatingUpdateEvent;
import com.specialist.profile.models.RatingUpdateEventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RatingUpdateEventMapper {
    RatingUpdateEventEntity toEntity(CreatorRatingUpdateEvent event);
}
