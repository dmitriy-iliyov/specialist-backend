package com.specialist.notification;

import com.specialist.contracts.notification.InternalAppointmentCancelEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public final class AppointmentCancelEventListener {

    private final AppointmentCancelEventHandler handler;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void listen(InternalAppointmentCancelEvent event) {
        handler.handle(event);
    }
}
