package com.specialist.specialistdirectory.infrastructure;

import com.specialist.contracts.auth.AccountDeleteEvent;
import com.specialist.contracts.auth.AccountDeleteHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public final class SpecialistDirectoryKafkaListener {

    private final AccountDeleteHandler accountDeleteHandler;

    public SpecialistDirectoryKafkaListener(
            @Qualifier("specialistDirectoryAccountDeleteHandler") AccountDeleteHandler accountDeleteHandler) {
        this.accountDeleteHandler = accountDeleteHandler;
    }

    @KafkaListener(topics = {"${api.kafka.topic.account-delete}"}, groupId = "specialist-directory-service")
    public void listen(AccountDeleteEvent event) {
        accountDeleteHandler.handle(event);
    }
}
