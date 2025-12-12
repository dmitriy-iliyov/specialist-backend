package com.specialist.profile.infrastructure;

import com.specialist.contracts.auth.AccountDeleteEvent;
import com.specialist.contracts.profile.CreatorRatingUpdateEvent;
import com.specialist.contracts.profile.ProfileType;
import com.specialist.profile.services.ProfileDeleteService;
import com.specialist.profile.services.rating.CreatorRatingService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ProfileEventListener {

    private final CreatorRatingService creatorRatingService;
    private final ProfileDeleteService profileDeleteService;

    public ProfileEventListener(@Qualifier("creatorRatingRetryDecorator") CreatorRatingService creatorRatingService,
                                ProfileDeleteService profileDeleteService) {
        this.creatorRatingService = creatorRatingService;
        this.profileDeleteService = profileDeleteService;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void listenRatingUpdate(CreatorRatingUpdateEvent event) {
        creatorRatingService.updateById(event);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void listenAccountDelete(AccountDeleteEvent event) {
        profileDeleteService.delete(
                event.accountId(),
                ProfileType.fromStringRole(event.stringRole())
        );
    }
}
