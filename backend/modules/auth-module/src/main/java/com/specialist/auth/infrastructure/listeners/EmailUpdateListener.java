package com.specialist.auth.infrastructure.listeners;

import com.specialist.auth.domain.account.models.events.EmailUpdatedEvent;
import com.specialist.auth.infrastructure.message.services.ConfirmationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public final class EmailUpdateListener {

    private final ConfirmationService confirmationService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleEmailUpdate(EmailUpdatedEvent event) {
        confirmationService.sendConfirmationCode(event.email());
    }
}
