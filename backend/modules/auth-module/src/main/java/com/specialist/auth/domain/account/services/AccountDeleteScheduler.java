package com.specialist.auth.domain.account.services;

import com.specialist.auth.domain.account.models.enums.AccountDeleteTaskStatus;
import com.specialist.contracts.auth.AccountDeleteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountDeleteScheduler {

    @Value("${api.account-delete.batch-size}")
    private int BATCH_SIZE;

    @Value("${api.kafka.topic.account-delete}")
    public String TOPIC;

    private final AccountDeleteTaskService service;
    private final KafkaTemplate<String, AccountDeleteEvent> kafkaTemplate;

    @Scheduled(
            initialDelayString = "${api.account-delete.initial_delay}",
            fixedDelayString = "${api.account-delete.fixed_delay}"
    )
    public void sendEvents() {
        List<AccountDeleteEvent> events = service.findBatchByStatus(AccountDeleteTaskStatus.READY_TO_SEND, BATCH_SIZE);
        if (events.isEmpty()) {
            return;
        }
        // изменить состояние на в процессе
        // отправить в кафку фсинхронно все
        // выставить тайм аут
        // обновить статус
        Set<UUID> ids = new HashSet<>();
        events.forEach(event -> {
            try {
                kafkaTemplate.send(TOPIC, event).get(20, TimeUnit.SECONDS);
                ids.add(event.id());
            } catch (Exception e) {
                log.error("Error send account delete event with id={}. ", event.id(), e);
            }
        });
        if (!ids.isEmpty()) {
            service.updateStatusBatchByIdIn(AccountDeleteTaskStatus.SENT, ids);
        }
    }
}
