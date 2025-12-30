package com.specialist.specialistdirectory.infrastructure;

import com.specialist.contracts.auth.DeferAccountDeleteEvent;
import com.specialist.contracts.auth.DeferAccountDeleteHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
public class SpecialistDirectoryEventListener {

    private final DeferAccountDeleteHandler accountDeleteHandler;

    public SpecialistDirectoryEventListener(@Qualifier("specialistDirectoryAccountDeleteHandler") DeferAccountDeleteHandler accountDeleteHandler) {
        this.accountDeleteHandler = accountDeleteHandler;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void listen(List<DeferAccountDeleteEvent> events) {
        accountDeleteHandler.handle(events);
    }
}
