package com.aidcompass.users.general.database_initalizers;

import com.aidcompass.users.profile_status.ProfileStatusService;
import com.aidcompass.users.profile_status.models.ProfileStatus;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class ProfileStatusDatabaseInitializer {

    private final ProfileStatusService service;


    @PostConstruct
    public void setUpStatuses() {
        service.saveAll(Arrays.stream(ProfileStatus.values()).toList());
    }
}