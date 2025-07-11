package com.aidcompass.users.general.database_initalizers;

import com.aidcompass.users.jurist.specialization.JuristTypeService;
import com.aidcompass.users.jurist.specialization.models.JuristType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class JuristTypeDataBaseInitializer {

    private final JuristTypeService service;


    @PostConstruct
    public void setUpSpecializations() {
        List<JuristType> typeList = new ArrayList<>(List.of(JuristType.values()));
        service.saveAll(typeList);
    }
}