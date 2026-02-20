package com.specialist.auth.domain.account.infrastructure;

import com.specialist.contracts.auth.DeferAccountDeleteEvent;
import com.specialist.contracts.auth.ImmediatelyAccountDeleteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultAccountDeleteEventSender implements AccountDeleteEventSender {

    private final ApplicationEventPublisher eventPublisher;
    private final TransactionTemplate transactionTemplate;

    @Override
    public void sendImmediately(List<ImmediatelyAccountDeleteEvent> events) {
        transactionTemplate.executeWithoutResult(status -> eventPublisher.publishEvent(events));
    }

    @Override
    public void sendDefer(List<DeferAccountDeleteEvent> events) {
        transactionTemplate.executeWithoutResult(status -> eventPublisher.publishEvent(events));
    }
}
