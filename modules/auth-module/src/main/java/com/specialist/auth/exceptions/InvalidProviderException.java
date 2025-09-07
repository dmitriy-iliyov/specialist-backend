package com.specialist.auth.exceptions;

import com.specialist.auth.core.oauth2.models.Provider;
import com.specialist.core.exceptions.models.BaseForbiddenException;
import com.specialist.core.exceptions.models.dto.ErrorDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InvalidProviderException extends BaseForbiddenException {

    private final Provider provider;

    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto(
                "provider",
                "Account registered with " + provider.getRegistrationId() + " provider,another login provider is forbidden."
        );
    }
}
