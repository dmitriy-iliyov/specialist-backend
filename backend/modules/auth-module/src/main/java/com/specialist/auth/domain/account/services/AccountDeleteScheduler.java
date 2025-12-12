package com.specialist.auth.domain.account.services;

import com.specialist.auth.domain.account.models.enums.AccountDeleteTaskStatus;
import com.specialist.contracts.auth.AccountDeleteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountDeleteScheduler {

    @Value("${api.account-delete.batch-size}")
    private int BATCH_SIZE;

    private final AccountDeleteTaskService service;
    private final ApplicationEventPublisher eventPublisher;
    private final TransactionTemplate transactionTemplate;

    @Scheduled(
            initialDelayString = "${api.account-delete.initial_delay}",
            fixedDelayString = "${api.account-delete.fixed_delay}"
    )
    public void publishEvents() {
        List<AccountDeleteEvent> events = service.findBatchByStatus(AccountDeleteTaskStatus.READY_TO_SEND, BATCH_SIZE);
        if (events.isEmpty()) {
            return;
        }
        Set<UUID> successIds = new HashSet<>();
        Set<UUID> failedIds = new HashSet<>();
        events.forEach(event -> {
            try {
                transactionTemplate.executeWithoutResult(status -> eventPublisher.publishEvent(event));
                successIds.add(event.id());
            } catch (Exception e) {
                log.error("Error send account delete event with id={}. ", event.id(), e);
                failedIds.add(event.id());
            }
        });
        if (!successIds.isEmpty()) {
            service.updateStatusBatchByIdIn(AccountDeleteTaskStatus.SENT, successIds);
        }
        if (!failedIds.isEmpty()) {
            service.updateStatusBatchByIdIn(AccountDeleteTaskStatus.READY_TO_SEND, failedIds);
        }
    }
}
