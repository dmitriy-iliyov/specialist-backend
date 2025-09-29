package com.specialist.profile.services.system;

import com.specialist.contracts.profile.ProfileEmailUpdateService;
import com.specialist.contracts.profile.ProfileType;
import com.specialist.profile.exceptions.NullStrategyException;
import com.specialist.profile.services.EmailUpdateStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProfileEmailUpdateServiceImpl implements ProfileEmailUpdateService {

    private final Map<ProfileType, EmailUpdateStrategy> strategyMap;

    public ProfileEmailUpdateServiceImpl(List<EmailUpdateStrategy> services) {
        this.strategyMap = services.stream()
                .collect(Collectors.toMap(EmailUpdateStrategy::getType, Function.identity()));
    }

    @Override
    public void updateById(ProfileType type, UUID id, String email) {
        EmailUpdateStrategy strategy = strategyMap.get(type);
        if (strategy == null) {
            log.error("SystemEmailUpdateServiceNotFoundException for user type {}", type);
            throw new NullStrategyException();
        }
        strategy.updateById(id, email);
    }
}
