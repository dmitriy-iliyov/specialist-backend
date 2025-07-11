package com.aidcompass.users.doctor.services;

import com.aidcompass.core.general.contracts.dto.BaseSystemVolunteerDto;

import java.util.UUID;

public interface DoctorApprovalService {
    BaseSystemVolunteerDto approve(UUID id);
}
