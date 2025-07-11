package com.aidcompass.users.customer.services;

import com.aidcompass.users.customer.models.CustomerDto;
import com.aidcompass.users.customer.models.PrivateCustomerResponseDto;
import com.aidcompass.users.detail.models.DetailEntity;
import com.aidcompass.users.general.interfaces.PersistService;

import java.util.UUID;

public interface CustomerPersistService extends PersistService<CustomerDto, PrivateCustomerResponseDto> {
    void save(UUID id, DetailEntity detail);
}
