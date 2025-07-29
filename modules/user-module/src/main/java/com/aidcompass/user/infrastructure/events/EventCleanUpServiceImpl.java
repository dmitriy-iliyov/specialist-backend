package com.aidcompass.user.infrastructure.events;

import com.aidcompass.user.infrastructure.ReviewBufferService;
import com.aidcompass.user.models.enums.ProcessingStatus;
import com.aidcompass.user.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventCleanUpServiceImpl implements EventCleanUpService {

    @Value("${api.review-buffer.clean.batch-size}")
    public int CLEAN_BATCH_SIZE;
    private final EventService service;
    private final ReviewBufferService restClient;


    @Scheduled(cron = "0 */2 1 * * *")
    @Override
    public void cleanUpRatingUpdateEvents() {
        Set<UUID> idsToDelete = service.findBatchByStatus(ProcessingStatus.PROCESSED, CLEAN_BATCH_SIZE);
        if (idsToDelete.isEmpty()) {
            return;
        }
        restClient.deleteBatchByIds(idsToDelete);
        service.deleteBatchByIds(idsToDelete);
    }
}
