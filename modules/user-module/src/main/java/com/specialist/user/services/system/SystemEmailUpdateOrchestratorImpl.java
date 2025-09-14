package com.specialist.user.services.system;

import com.specialist.contracts.user.ProfileType;
import com.specialist.contracts.user.SystemEmailUpdateOrchestrator;
import com.specialist.user.exceptions.SystemEmailUpdateServiceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SystemEmailUpdateOrchestratorImpl implements SystemEmailUpdateOrchestrator {

    private final Map<ProfileType, SystemEmailUpdateService> serviceMap;

    public SystemEmailUpdateOrchestratorImpl(List<SystemEmailUpdateService> services) {
        this.serviceMap = services.stream()
                .collect(Collectors.toMap(SystemEmailUpdateService::getType, Function.identity()));
    }

    @Override
    public void updateById(ProfileType type, UUID id, String email) {
        SystemEmailUpdateService service = serviceMap.get(type);
        if (service == null) {
            log.error("SystemEmailUpdateServiceNotFoundException for user type {}", type);
            throw new SystemEmailUpdateServiceNotFoundException();
        }
        service.updateById(id, email);
    }
}
