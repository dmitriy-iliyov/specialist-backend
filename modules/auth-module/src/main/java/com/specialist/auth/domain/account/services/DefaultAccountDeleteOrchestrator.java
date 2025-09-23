package com.specialist.auth.domain.account.services;

import com.specialist.contracts.auth.AccountDeleteEvent;
import com.specialist.utils.UuidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultAccountDeleteOrchestrator implements AccountDeleteOrchestrator {

    @Value("${api.kafka.topic.account-delete}")
    public String TOPIC;
    private final AccountService accountService;
    private final KafkaTemplate<String, AccountDeleteEvent> kafkaTemplate;

    @Override
    public void delete(UUID id) {
        String stringRole = accountService.findRoleById(id).toString();
        AccountDeleteEvent event = new AccountDeleteEvent(UuidUtils.generateV7(), id, stringRole);
        accountService.deleteById(id);
        kafkaTemplate.send(TOPIC, event);
    }
}
