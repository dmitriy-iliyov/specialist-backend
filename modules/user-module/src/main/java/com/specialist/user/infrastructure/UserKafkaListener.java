package com.specialist.user.infrastructure;

import com.specialist.contracts.user.CreatorRatingUpdateEvent;
import com.specialist.user.services.CreatorRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class UserKafkaListener {

    private final CreatorRatingService creatorRatingService;

    @Autowired
    public UserKafkaListener(@Qualifier("creatorRatingServiceDecorator") CreatorRatingService creatorRatingService) {
        this.creatorRatingService = creatorRatingService;
    }

    @KafkaListener(topics = "${api.kafka.topic.creator-rating}", groupId = "spring.kafka.consumer.group-id")
    public void listen(CreatorRatingUpdateEvent event, Acknowledgment ack) {
        creatorRatingService.updateById(event);
        ack.acknowledge();
    }
}
