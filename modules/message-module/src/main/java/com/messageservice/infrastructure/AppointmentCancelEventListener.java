package com.messageservice.infrastructure;

import com.messageservice.services.AppointmentCancelHandler;
import com.specialist.contracts.notification.ExternalAppointmentCancelEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class AppointmentCancelEventListener {

    private final AppointmentCancelHandler appointmentCancelHandler;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void listen(ExternalAppointmentCancelEvent event) throws Exception {
        appointmentCancelHandler.handle(event);
    }
}
