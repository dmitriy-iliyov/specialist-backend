package com.aidcompass.core.contact.core.config;

import com.aidcompass.core.contact.core.services.SystemContactService;
import com.aidcompass.core.contact.core.validation.validators.ContactOwnershipValidator;
import com.aidcompass.core.contact.core.validation.validators.ContactUniquenessValidator;
import com.aidcompass.core.contact.core.validation.validators.FormatValidator;
import com.aidcompass.core.contact.core.validation.validators.PermissionValidator;
import com.aidcompass.core.contact.core.validation.validators.impl.PermissionValidatorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContactConfig {

    @Bean
    public PermissionValidator permissionValidator(SystemContactService service,
                                                   ContactOwnershipValidator contactOwnershipValidator,
                                                   ContactUniquenessValidator contactUniquenessValidator,
                                                   FormatValidator formatValidator) {
        return new PermissionValidatorImpl(service, contactOwnershipValidator, contactUniquenessValidator, formatValidator);
    }
}
