package com.specialist.user.services.system;

import com.specialist.contracts.user.UserType;
import com.specialist.user.repositories.SpecialistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SystemSpecialistEmailUpdateService implements SystemEmailUpdateService {

    private final SpecialistRepository repository;

    @Transactional
    @Override
    public void updateById(UUID id, String email) {
        repository.updateEmailById(id, email);
    }

    @Override
    public UserType getType() {
        return UserType.SPECIALIST;
    }
}
