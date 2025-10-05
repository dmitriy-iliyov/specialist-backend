package com.specialist.profile.services.rating;

import com.specialist.contracts.specialistdirectory.SystemCreatorRatingBufferService;
import com.specialist.profile.models.enums.ProcessingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreatorRatingEventScheduler {

    @Value("${api.creator-rating-buffer.processed.clean.batch-size}")
    public int CLEAN_BATCH_SIZE;

    @Value("${api.creator-rating-buffer.not-processed.resend.batch-size}")
    public int NOTIFY_BATCH_SIZE;

    private final CreatorRatingEventService service;
    private final SystemCreatorRatingBufferService bufferService;

    @Scheduled(cron = "0 */2 1 * * *")
    public void notifyToResend() {
        Set<UUID> idsToResend = service.findBatchByStatus(ProcessingStatus.FAILED, NOTIFY_BATCH_SIZE);
        if (idsToResend.isEmpty()) {
            return;
        }
        bufferService.resendAllByIdIn(idsToResend);
    }

    @Scheduled(cron = "0 */2 2 * * *")
    public void cleanUpProcessed() {
        Set<UUID> idsToDelete = service.findBatchByStatus(ProcessingStatus.PROCESSED, CLEAN_BATCH_SIZE);
        if (idsToDelete.isEmpty()) {
            return;
        }
        bufferService.deleteAllByIdIn(idsToDelete);
        service.deleteBatchByIds(idsToDelete);
    }
}
