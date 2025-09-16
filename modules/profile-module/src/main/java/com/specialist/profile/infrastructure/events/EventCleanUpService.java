package com.specialist.profile.infrastructure.events;

import com.specialist.profile.infrastructure.rest.ReviewBufferService;
import com.specialist.profile.models.enums.ProcessingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventCleanUpService {

    @Value("${api.review-buffer.clean.batch-size}")
    public int CLEAN_BATCH_SIZE;

    @Value("${api.review-buffer.clean.batch-size}")
    public int NOTIFY_BATCH_SIZE;

    private final EventService service;
    private final ReviewBufferService restClient;

    @Scheduled(cron = "0 */2 1 * * *")
    public void notifyToResend() {
        Set<UUID> idsToResend = service.findBatchByStatus(ProcessingStatus.FAILED, NOTIFY_BATCH_SIZE);
        if (idsToResend.isEmpty()) {
            return;
        }
        restClient.notifyToResend(idsToResend);
    }

    @Scheduled(cron = "0 */2 2 * * *")
    public void cleanUpProcessed() {
        Set<UUID> idsToDelete = service.findBatchByStatus(ProcessingStatus.PROCESSED, CLEAN_BATCH_SIZE);
        if (idsToDelete.isEmpty()) {
            return;
        }
        restClient.deleteBatchByIds(idsToDelete);
        service.deleteBatchByIds(idsToDelete);
    }
}
