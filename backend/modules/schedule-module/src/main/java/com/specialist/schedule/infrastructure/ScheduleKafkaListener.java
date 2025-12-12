package com.specialist.schedule.infrastructure;

import com.specialist.contracts.auth.AccountDeleteEvent;
import com.specialist.contracts.auth.AccountDeleteHandler;
import io.github.dmitriyiliyov.springoutbox.consumer.OutboxIdempotentConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Deprecated(forRemoval = true)
public class ScheduleKafkaListener {

    private final AccountDeleteHandler accountDeleteHandler;
    private final OutboxIdempotentConsumer outboxConsumer;

    public ScheduleKafkaListener(@Qualifier("scheduleAccountDeleteHandler") AccountDeleteHandler accountDeleteHandler,
                                 OutboxIdempotentConsumer outboxConsumer) {
        this.accountDeleteHandler = accountDeleteHandler;
        this.outboxConsumer = outboxConsumer;
        throw new IllegalStateException("Shouldn't created");
    }

    //@KafkaListener(topics = {"${api.kafka.topic.accounts-deleted}"}, groupId = "schedule-service")
    public void listen(ConsumerRecord<String, AccountDeleteEvent> record) {
        outboxConsumer.consume(record, () -> accountDeleteHandler.handle(record.value()));
    }
}
