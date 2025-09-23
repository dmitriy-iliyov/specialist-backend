package com.specialist.profile.infrastructure;

import com.specialist.contracts.auth.AccountDeleteEvent;
import com.specialist.contracts.profile.CreatorRatingUpdateEvent;
import com.specialist.contracts.profile.ProfileType;
import com.specialist.profile.services.ProfileDeleteOrchestrator;
import com.specialist.profile.services.rating.CreatorRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public final class ProfileKafkaListener {

    private final CreatorRatingService creatorRatingService;
    private final ProfileDeleteOrchestrator profileDeleteOrchestrator;

    @Autowired
    public ProfileKafkaListener(@Qualifier("creatorRatingRetryDecorator") CreatorRatingService creatorRatingService,
                                ProfileDeleteOrchestrator profileDeleteOrchestrator) {
        this.creatorRatingService = creatorRatingService;
        this.profileDeleteOrchestrator = profileDeleteOrchestrator;
    }

    @KafkaListener(topics = "${api.kafka.topic.creator-rating}", groupId = "profile-service")
    public void listen(CreatorRatingUpdateEvent event) {
        creatorRatingService.updateById(event);
    }

    @KafkaListener(topics = {"${api.kafka.topic.account-delete}"}, groupId = "profile-service")
    public void listen(AccountDeleteEvent event) {
        profileDeleteOrchestrator.delete(event.accountId(), ProfileType.fromStringRole(event.stringRole()));
    }
}
