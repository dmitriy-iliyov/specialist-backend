package com.specialist.schedule.infrastructure;

import com.specialist.contracts.auth.AccountDeleteEvent;
import com.specialist.contracts.auth.AccountDeleteHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ScheduleEventListener {

    private final AccountDeleteHandler accountDeleteHandler;

    public ScheduleEventListener(@Qualifier("scheduleAccountDeleteHandler") AccountDeleteHandler accountDeleteHandler) {
        this.accountDeleteHandler = accountDeleteHandler;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void listen(AccountDeleteEvent event) {
        accountDeleteHandler.handle(event);
    }
}
