package com.specialist.user.services.system;

import com.specialist.contracts.user.UserType;
import com.specialist.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SystemUserEmailUpdateService implements SystemEmailUpdateService {

    private final UserRepository repository;

    @Transactional
    @Override
    public void updateById(UUID id, String email) {
        repository.updateEmailById(id, email);
    }

    @Override
    public UserType getType() {
        return UserType.USER;
    }
}
