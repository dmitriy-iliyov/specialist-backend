package com.aidcompass.users.general.exceptions.doctor;

import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class DoctorSpecializationEntityNotFoundBySpecializationException extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return null;
    }
}
