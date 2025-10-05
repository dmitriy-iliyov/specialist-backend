package com.specialist.schedule.infrastructure;

import com.specialist.contracts.auth.AccountDeleteEvent;
import com.specialist.contracts.auth.AccountDeleteHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public final class ScheduleKafkaListener {

    private final AccountDeleteHandler accountDeleteHandler;

    public ScheduleKafkaListener(@Qualifier("scheduleAccountDeleteHandler") AccountDeleteHandler accountDeleteHandler) {
        this.accountDeleteHandler = accountDeleteHandler;
    }

    @KafkaListener(topics = {"${api.kafka.topic.account-delete}"}, groupId = "schedule-service")
    public void listen(AccountDeleteEvent event) {
        accountDeleteHandler.handle(event);
    }
}
