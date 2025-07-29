package com.aidcompass.user.infrastructure;

import com.aidcompass.contracts.user.CreatorRatingUpdateEvent;
import com.aidcompass.user.services.CreatorRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserKafkaListener {

    private final CreatorRatingService creatorRatingService;

    @Autowired
    public UserKafkaListener(@Qualifier("creatorRatingServiceDecorator") CreatorRatingService creatorRatingService) {
        this.creatorRatingService = creatorRatingService;
    }

    @KafkaListener(topics = {"${api.kafka.topic.review-buffer}"})
    public void listen(CreatorRatingUpdateEvent event) {
        creatorRatingService.updateById(event);
    }
}
