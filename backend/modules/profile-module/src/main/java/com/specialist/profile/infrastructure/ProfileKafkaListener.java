package com.specialist.profile.infrastructure;

import com.specialist.contracts.auth.AccountDeleteEvent;
import com.specialist.contracts.profile.CreatorRatingUpdateEvent;
import com.specialist.contracts.profile.ProfileType;
import com.specialist.profile.services.ProfileDeleteService;
import com.specialist.profile.services.rating.CreatorRatingService;
import io.github.dmitriyiliyov.springoutbox.consumer.OutboxIdempotentConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Deprecated(forRemoval = true)
public final class ProfileKafkaListener {

    private final CreatorRatingService creatorRatingService;
    private final ProfileDeleteService profileDeleteService;
    private final OutboxIdempotentConsumer outboxConsumer;

    public ProfileKafkaListener(@Qualifier("creatorRatingRetryDecorator") CreatorRatingService creatorRatingService,
                                ProfileDeleteService profileDeleteService, OutboxIdempotentConsumer outboxConsumer) {
        this.creatorRatingService = creatorRatingService;
        this.profileDeleteService = profileDeleteService;
        this.outboxConsumer = outboxConsumer;
        throw new IllegalStateException("Shouldn't created");
    }

    //@KafkaListener(topics = "${api.kafka.topic.creator-rating}", groupId = "profile-service")
    public void listenRatingUpdate(ConsumerRecord<String, CreatorRatingUpdateEvent> record) {
        outboxConsumer.consume(record, () -> creatorRatingService.updateById(record.value()));
    }

    //@KafkaListener(topics = {"${api.kafka.topic.accounts-deleted}"}, groupId = "profile-service")
    public void listenAccountDelete(ConsumerRecord<String, AccountDeleteEvent> record) {
        outboxConsumer.consume(
                record,
                () -> profileDeleteService.delete(
                        record.value().accountId(),
                        ProfileType.fromStringRole(record.value().stringRole())
                )
        );
    }
}
