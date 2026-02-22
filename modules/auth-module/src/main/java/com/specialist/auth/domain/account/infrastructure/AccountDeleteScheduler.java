package com.specialist.auth.domain.account.infrastructure;

import com.specialist.auth.domain.account.models.enums.AccountDeleteTaskStatus;
import com.specialist.auth.domain.account.models.enums.DisableReason;
import com.specialist.auth.domain.account.services.AccountService;
import com.specialist.contracts.auth.DeferAccountDeleteEvent;
import com.specialist.contracts.auth.ImmediatelyAccountDeleteEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccountDeleteScheduler {

    private final AccountDeleteTaskService service;
    private final AccountDeleteEventSender sender;
    private final AccountService accountService;
    private final int deletePropagationBatchSize;
    private final int stuckBatchSize;
    private final Duration ttlBeforeHardDelete;
    private final int hardDeleteBatchSize;

    public AccountDeleteScheduler(AccountDeleteTaskService service, AccountDeleteEventSender sender, AccountService accountService,
                                  @Value("${api.account-delete.propagation-batch-size}") int deletePropagationBatchSize,
                                  @Value("${api.account-delete.stuck-batch-size}") int stuckBatchSize,
                                  @Value("${api.account-delete.ttl-before-hard-delete}") Duration ttlBeforeHardDelete,
                                  @Value("${api.account-delete.hard-delete-batch-size}") int hardDeleteBatchSize) {
        this.service = service;
        this.sender = sender;
        this.accountService = accountService;
        this.hardDeleteBatchSize = hardDeleteBatchSize;
        this.ttlBeforeHardDelete = ttlBeforeHardDelete;
        this.stuckBatchSize = stuckBatchSize;
        this.deletePropagationBatchSize = deletePropagationBatchSize;
    }

    @Scheduled(
            initialDelayString = "${api.account-delete.propagation.initial_delay}",
            fixedDelayString = "${api.account-delete.propagation.fixed_delay}"
    )
    public void publishSoftDeleteEvents() {
        List<ImmediatelyAccountDeleteEvent> events = service.findBatchByStatus(
                AccountDeleteTaskStatus.READY_TO_SEND, AccountDeleteTaskStatus.SENDING, deletePropagationBatchSize
        );
        if (events.isEmpty()) {
            return;
        }
        Set<UUID> ids = events.stream().map(ImmediatelyAccountDeleteEvent::id).collect(Collectors.toSet());
        try {
            sender.sendImmediately(events);
        } catch (Exception e) {
            log.error("Error send account delete event with id={}. ", ids, e);
            service.updateStatusBatchByIdIn(AccountDeleteTaskStatus.READY_TO_SEND, ids);
        } finally {
            service.updateStatusBatchByIdIn(AccountDeleteTaskStatus.SENT, ids);
        }
    }

    @Scheduled(
            initialDelayString = "${api.account-delete.recover-stuck.initial_delay}",
            fixedDelayString = "${api.account-delete.recover-stuck.fixed_delay}"
    )
    public void recoverStuckEvents() {
        service.findBatchByStatus(
                AccountDeleteTaskStatus.SENDING, AccountDeleteTaskStatus.READY_TO_SEND, stuckBatchSize
        );
    }

    @Scheduled(
            initialDelayString = "${api.account-delete.hard-delete.initial_delay}",
            fixedDelayString = "${api.account-delete.hard-delete.fixed_delay}"
    )
    public void publishHardDeleteEvents() {
        Instant threshold = Instant.now().minusSeconds(ttlBeforeHardDelete.toSeconds());
        List<DeferAccountDeleteEvent> accounts = accountService.findAllByDisableReasonAndThreshold(
                DisableReason.SOFT_DELETE,
                threshold,
                hardDeleteBatchSize
        );
        accountService.deleteAllByIdIn(
                accounts.stream()
                        .map(DeferAccountDeleteEvent::accountId)
                        .collect(Collectors.toSet())
        );
        sender.sendDefer(accounts);
    }
}
