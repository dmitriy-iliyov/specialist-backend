package com.specialist.auth.domain.account.infrastructure;

import com.specialist.auth.domain.account.models.dtos.AccountResponseDto;
import com.specialist.auth.domain.account.models.enums.AccountDeleteTaskStatus;
import com.specialist.auth.domain.account.models.enums.DisableReason;
import com.specialist.auth.domain.account.services.AccountService;
import com.specialist.contracts.auth.DeferAccountDeleteEvent;
import com.specialist.contracts.auth.ImmediatelyAccountDeleteEvent;
import lombok.RequiredArgsConstructor;
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
    private final AccountDeleteEventSender sender;
    private final AccountService accountService;

    @Scheduled(
            initialDelayString = "${api.account-delete.propagation.initial_delay}",
            fixedDelayString = "${api.account-delete.propagation.fixed_delay}"
    )
    public void publishSoftDeleteEvents() {
        List<ImmediatelyAccountDeleteEvent> events = service.findBatchByStatus(
                AccountDeleteTaskStatus.READY_TO_SEND, AccountDeleteTaskStatus.SENDING, DELETE_PROPAGATION_BATCH_SIZE
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
                AccountDeleteTaskStatus.SENDING, AccountDeleteTaskStatus.READY_TO_SEND, STUCK_BATCH_SIZE
        );
    }

    @Scheduled(
            initialDelayString = "${api.account-delete.hard-delete.initial_delay}",
            fixedDelayString = "${api.account-delete.hard-delete.fixed_delay}"
    )
    public void publishHardDeleteEvents() {
        Instant threshold = Instant.now().minusSeconds(TTL_BEFORE_HARD_DELETE.toSeconds());
        List<DeferAccountDeleteEvent> accounts = accountService.findAllByDisableReasonAndThreshold(
                DisableReason.SOFT_DELETE,
                threshold,
                HARD_DELETE_BATCH_SIZE
        );
        accountService.deleteAllByIdIn(
                accounts.stream()
                        .map(DeferAccountDeleteEvent::accountId)
                        .collect(Collectors.toSet())
        );
        sender.sendDefer(accounts);
    }
}
