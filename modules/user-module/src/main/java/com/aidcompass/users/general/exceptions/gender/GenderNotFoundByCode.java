package com.aidcompass.users.general.exceptions.gender;

import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class GenderNotFoundByCode extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("gender", "Gender not found by code!");
    }
}
