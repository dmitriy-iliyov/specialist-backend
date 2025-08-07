package com.aidcompass.auth.domain.service_account;

import com.aidcompass.auth.domain.service_account.models.ServiceAccountDto;
import com.aidcompass.auth.domain.service_account.models.ServiceAccountResponseDto;
import com.aidcompass.utils.pagination.PageRequest;
import com.aidcompass.utils.pagination.PageResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface ServiceAccountService {
    ServiceAccountResponseDto save(UUID adminId, ServiceAccountDto dto);

    @Transactional(readOnly = true)
    PageResponse<ServiceAccountResponseDto> findAll(PageRequest pageRequest);

    void deleteById(UUID id);
}
