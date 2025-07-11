package com.aidcompass.users.customer;

import com.aidcompass.users.customer.models.CustomerDto;
import com.aidcompass.users.customer.models.PrivateCustomerResponseDto;
import com.aidcompass.users.general.interfaces.PersistFacade;

import java.util.UUID;

public interface CustomerPersistFacade extends PersistFacade<CustomerDto, PrivateCustomerResponseDto> {
    void save(UUID id);
}
