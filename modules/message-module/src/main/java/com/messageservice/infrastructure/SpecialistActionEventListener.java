package com.messageservice.infrastructure;

import com.messageservice.services.SpecialistActionEventHandler;
import com.specialist.contracts.specialistdirectory.dto.SpecialistActionEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class SpecialistActionEventListener {

    private final SpecialistActionEventHandler processor;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void listen(SpecialistActionEvent event) throws Exception {
        processor.handel(event);
    }
}
