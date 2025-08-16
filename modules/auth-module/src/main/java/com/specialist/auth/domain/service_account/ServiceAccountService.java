package com.specialist.auth.domain.service_account;

import com.specialist.auth.domain.service_account.models.SecretServiceAccountResponseDto;
import com.specialist.auth.domain.service_account.models.ServiceAccountDto;
import com.specialist.auth.domain.service_account.models.ServiceAccountResponseDto;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface ServiceAccountService {
    SecretServiceAccountResponseDto save(UUID adminId, ServiceAccountDto dto);

    PageResponse<ServiceAccountResponseDto> findAll(PageRequest pageRequest);

    void deleteById(UUID id);
}
