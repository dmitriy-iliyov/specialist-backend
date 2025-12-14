//package com.specialist.specialistdirectory.infrastructure;
//
//import com.specialist.contracts.auth.AccountDeleteEvent;
//import com.specialist.contracts.auth.AccountDeleteHandler;
//import io.github.dmitriyiliyov.springoutbox.consumer.OutboxIdempotentConsumer;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.kafka.annotation.KafkaListener;
//
//
//@Deprecated(forRemoval = true)
//public final class SpecialistDirectoryKafkaListener {
//
//    private final AccountDeleteHandler accountDeleteHandler;
//    private final OutboxIdempotentConsumer outboxConsumer;
//
//    public SpecialistDirectoryKafkaListener(
//            @Qualifier("specialistDirectoryAccountDeleteHandler") AccountDeleteHandler accountDeleteHandler,
//            OutboxIdempotentConsumer outboxConsumer
//    ) {
//        this.accountDeleteHandler = accountDeleteHandler;
//        this.outboxConsumer = outboxConsumer;
//        throw new IllegalStateException("Shouldn't created");
//    }
//
//    @KafkaListener(topics = {"${api.kafka.topic.accounts-deleted}"}, groupId = "specialist-directory-service")
//    public void listen(ConsumerRecord<String, AccountDeleteEvent> record) {
//        outboxConsumer.consume(record, () -> accountDeleteHandler.handle(record.value()));
//    }
//}
