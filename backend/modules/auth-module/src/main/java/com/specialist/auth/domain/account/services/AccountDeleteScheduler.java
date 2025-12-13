package com.specialist.auth.domain.account.services;

import com.specialist.auth.domain.account.models.enums.AccountDeleteTaskStatus;
import com.specialist.auth.domain.account.models.enums.DisableReason;
import com.specialist.contracts.auth.AccountDeleteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountDeleteScheduler {

    @Value("${api.account-delete.propagation-batch-size}")
    public int DELETE_PROPAGATION_BATCH_SIZE;

    @Value("${api.account-delete.stuck-batch-size}")
    public int STUCK_BATCH_SIZE;

    @Value("${api.account-delete.ttl-before-hard-delete}")
    public Duration TTL_BEFORE_HARD_DELETE;

    @Value("${api.account-delete.hard-delete-batch-size}")
    public int HARD_DELETE_BATCH_SIZE;

    private final AccountDeleteTaskService service;
    private final ApplicationEventPublisher eventPublisher;
    private final TransactionTemplate transactionTemplate;
    private final AccountService accountService;

    @Scheduled(
            initialDelayString = "${api.account-delete.propagation.initial_delay}",
            fixedDelayString = "${api.account-delete.propagation.fixed_delay}"
    )
    public void publishEvents() {
        List<AccountDeleteEvent> events = service.findBatchByStatus(
                AccountDeleteTaskStatus.READY_TO_SEND, AccountDeleteTaskStatus.SENDING, DELETE_PROPAGATION_BATCH_SIZE
        );
        if (events.isEmpty()) {
            return;
        }
        Set<UUID> successIds = new HashSet<>();
        Set<UUID> failedIds = new HashSet<>();
        try {
            events.forEach(event -> {
                try {
                    transactionTemplate.executeWithoutResult(status -> eventPublisher.publishEvent(event));
                    successIds.add(event.id());
                } catch (Exception e) {
                    log.error("Error send account delete event with id={}. ", event.id(), e);
                    failedIds.add(event.id());
                }
            });
        } finally {
            if (!successIds.isEmpty()) {
                service.updateStatusBatchByIdIn(AccountDeleteTaskStatus.SENT, successIds);
            }
            if (!failedIds.isEmpty()) {
                service.updateStatusBatchByIdIn(AccountDeleteTaskStatus.READY_TO_SEND, failedIds);
            }
        }
    }

    @Scheduled(
            initialDelayString = "${api.account-delete.recover-stuck.initial_delay}",
            fixedDelayString = "${api.account-delete.recover-stuck.fixed_delay}"
    )
    public void recoverStuckEvents() {
        service.findBatchByStatus(
                AccountDeleteTaskStatus.SENDING, AccountDeleteTaskStatus.READY_TO_SEND, STUCK_BATCH_SIZE
        );
    }

    @Scheduled(
            initialDelayString = "${api.account-delete.hard-delete.initial_delay}",
            fixedDelayString = "${api.account-delete.hard-delete.fixed_delay}"
    )
    public void hardDelete() {
        Instant threshold = Instant.now().minusSeconds(TTL_BEFORE_HARD_DELETE.toSeconds());
        accountService.hardDeleteBatch(DisableReason.SOFT_DELETE, threshold, HARD_DELETE_BATCH_SIZE);
    }
}
