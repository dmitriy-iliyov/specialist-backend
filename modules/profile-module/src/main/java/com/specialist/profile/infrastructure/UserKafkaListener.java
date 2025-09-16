package com.specialist.profile.infrastructure;

import com.specialist.contracts.profile.CreatorRatingUpdateEvent;
import com.specialist.profile.services.rating.CreatorRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public final class UserKafkaListener {

    private final CreatorRatingService creatorRatingService;

    @Autowired
    public UserKafkaListener(@Qualifier("creatorRatingRetryDecorator") CreatorRatingService creatorRatingService) {
        this.creatorRatingService = creatorRatingService;
    }

    @KafkaListener(topics = "${api.kafka.topic.creator-rating}", groupId = "profile-service")
    public void listen(CreatorRatingUpdateEvent event) {
        creatorRatingService.updateById(event);
    }
}
