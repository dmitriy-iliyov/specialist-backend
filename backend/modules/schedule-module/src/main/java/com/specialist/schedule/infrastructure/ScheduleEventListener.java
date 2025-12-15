package com.specialist.schedule.infrastructure;

import com.specialist.contracts.auth.ImmediatelyAccountDeleteEvent;
import com.specialist.contracts.auth.ImmediatelyAccountDeleteHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
public class ScheduleEventListener {

    private final ImmediatelyAccountDeleteHandler immediatelyAccountDeleteHandler;

    public ScheduleEventListener(@Qualifier("scheduleAccountDeleteHandler") ImmediatelyAccountDeleteHandler immediatelyAccountDeleteHandler) {
        this.immediatelyAccountDeleteHandler = immediatelyAccountDeleteHandler;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void listen(List<ImmediatelyAccountDeleteEvent> event) {
        immediatelyAccountDeleteHandler.handle(event);
    }
}
