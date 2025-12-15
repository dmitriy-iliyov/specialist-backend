package com.specialist.profile.infrastructure;

import com.specialist.contracts.auth.AccountCreateEvent;
import com.specialist.contracts.auth.AccountCreateHandler;
import com.specialist.contracts.auth.DeferAccountDeleteEvent;
import com.specialist.contracts.auth.DeferAccountDeleteHandler;
import com.specialist.contracts.profile.CreatorRatingUpdateEvent;
import com.specialist.profile.services.rating.CreatorRatingService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
public class ProfileEventListener {

    private final CreatorRatingService creatorRatingService;
    private final DeferAccountDeleteHandler accountDeleteHandler;
    private final AccountCreateHandler accountCreateHandler;

    public ProfileEventListener(@Qualifier("creatorRatingRetryDecorator") CreatorRatingService creatorRatingService,
                                @Qualifier("profileAccountDeleteHandler") DeferAccountDeleteHandler accountDeleteHandler,
                                @Qualifier("defaultSystemProfileService") AccountCreateHandler accountCreateHandler) {
        this.creatorRatingService = creatorRatingService;
        this.accountDeleteHandler = accountDeleteHandler;
        this.accountCreateHandler = accountCreateHandler;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void listenAccountCreate(AccountCreateEvent event) {
        accountCreateHandler.handle(event);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void listenRatingUpdate(CreatorRatingUpdateEvent event) {
        creatorRatingService.updateById(event);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void listenAccountDelete(List<DeferAccountDeleteEvent> events) {
        accountDeleteHandler.handle(events);
    }
}
