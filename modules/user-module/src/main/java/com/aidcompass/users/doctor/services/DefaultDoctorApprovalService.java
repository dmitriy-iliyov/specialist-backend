package com.aidcompass.users.doctor.services;

import com.aidcompass.users.doctor.mapper.DoctorMapper;
import com.aidcompass.users.doctor.repository.DoctorRepository;
import com.aidcompass.core.general.contracts.dto.BaseSystemVolunteerDto;
import com.aidcompass.users.general.exceptions.doctor.DoctorNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultDoctorApprovalService implements DoctorApprovalService {

    private final DoctorRepository repository;
    private final DoctorMapper mapper;


    @Transactional
    @Override
    public BaseSystemVolunteerDto approve(UUID id) {
        repository.approveById(id);
        return mapper.toSystemDto(repository.findById(id).orElseThrow(DoctorNotFoundByIdException::new));
    }
}
