package com.aidcompass.specialistdirectory.domain.specialist_type.services;

import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeUpdateDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.services.interfases.TypeOrchestrator;
import com.aidcompass.specialistdirectory.domain.specialist_type.services.interfases.TypeService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class TypeOrchestratorImpl implements TypeOrchestrator {

    private final TypeService service;
    private final Validator validator;


    @Override
    public TypeDto update(TypeUpdateDto dto) {
        Set<ConstraintViolation<TypeUpdateDto>> bindingErrors = validator.validate(dto);
        if (!bindingErrors.isEmpty()) {
            throw new ConstraintViolationException(bindingErrors);
        }
        return service.update(dto);
    }
}
