package com.aidcompass.users.general.exceptions.jurist;

import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class JuristSpecializationEntityNotFoundBySpecializationException extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return null;
    }
}
