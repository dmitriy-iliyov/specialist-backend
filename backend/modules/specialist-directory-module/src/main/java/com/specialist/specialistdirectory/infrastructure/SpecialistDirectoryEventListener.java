package com.specialist.specialistdirectory.infrastructure;

import com.specialist.contracts.auth.AccountDeleteEvent;
import com.specialist.contracts.auth.AccountDeleteHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class SpecialistDirectoryEventListener {

    private final AccountDeleteHandler accountDeleteHandler;

    public SpecialistDirectoryEventListener(@Qualifier("specialistDirectoryAccountDeleteHandler") AccountDeleteHandler accountDeleteHandler) {
        this.accountDeleteHandler = accountDeleteHandler;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void listen(AccountDeleteEvent event) {
        accountDeleteHandler.handle(event);
    }
}
