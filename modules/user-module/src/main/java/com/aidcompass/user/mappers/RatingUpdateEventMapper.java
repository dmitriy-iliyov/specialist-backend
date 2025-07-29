package com.aidcompass.user.mappers;

import com.aidcompass.contracts.user.CreatorRatingUpdateEvent;
import com.aidcompass.user.models.RatingUpdateEventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RatingUpdateEventMapper {
    RatingUpdateEventEntity toEntity(CreatorRatingUpdateEvent event);
}
