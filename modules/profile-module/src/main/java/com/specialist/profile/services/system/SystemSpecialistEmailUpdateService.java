package com.specialist.profile.services.system;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.profile.repositories.SpecialistProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SystemSpecialistEmailUpdateService implements SystemEmailUpdateService {

    private final SpecialistProfileRepository repository;

    @Transactional
    @Override
    public void updateById(UUID id, String email) {
        repository.updateEmailById(id, email);
    }

    @Override
    public ProfileType getType() {
        return ProfileType.SPECIALIST;
    }
}
