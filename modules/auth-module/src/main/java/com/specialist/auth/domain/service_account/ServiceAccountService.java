package com.specialist.auth.domain.service_account;

import com.specialist.auth.domain.service_account.models.ServiceAccountDto;
import com.specialist.auth.domain.service_account.models.ServiceAccountResponseDto;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface ServiceAccountService {
    ServiceAccountResponseDto save(UUID adminId, ServiceAccountDto dto);

    @Transactional(readOnly = true)
    PageResponse<ServiceAccountResponseDto> findAll(PageRequest pageRequest);

    void deleteById(UUID id);
}
