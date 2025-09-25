package com.specialist.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public final class InternalAppointmentCancelEventListener {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void listen(InternalAppointmentCancelEvent event)
}
