package com.aidcompass.users.customer.services;

import com.aidcompass.users.customer.models.CustomerDto;
import com.aidcompass.users.customer.models.PrivateCustomerResponseDto;
import com.aidcompass.users.customer.models.PublicCustomerResponseDto;
import com.aidcompass.core.general.contracts.dto.PageResponse;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CustomerService extends CustomerPersistService {

    long count();

    PrivateCustomerResponseDto findPrivateById(UUID id);

    PublicCustomerResponseDto findPublicById(UUID id);

    List<PublicCustomerResponseDto> findAllByIds(Set<UUID> ids);

    PrivateCustomerResponseDto updateById(UUID userId, CustomerDto dto);

    void deleteById(UUID id);

    PageResponse<PrivateCustomerResponseDto> findAllByNamesCombination(String firstName, String secondName, String lastName,
                                                                       int page, int size);
}
