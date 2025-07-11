package com.aidcompass.users.doctor;

import com.aidcompass.users.detail.PersistEmptyDetailService;
import com.aidcompass.users.detail.models.DetailEntity;
import com.aidcompass.users.doctor.models.dto.DoctorDto;
import com.aidcompass.users.doctor.models.dto.PrivateDoctorResponseDto;
import com.aidcompass.users.general.interfaces.PersistFacade;
import com.aidcompass.users.general.interfaces.PersistService;
import com.aidcompass.core.security.auth.services.UserAuthService;
import com.aidcompass.core.security.domain.authority.models.Authority;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DoctorPersistFacade implements PersistFacade<DoctorDto, PrivateDoctorResponseDto> {

    private final PersistService<DoctorDto, PrivateDoctorResponseDto> doctorService;
    private final PersistEmptyDetailService detailService;
    private final UserAuthService userAuthService;


    @Transactional
    @Override
    public PrivateDoctorResponseDto save(UUID userId, DoctorDto dto, HttpServletResponse response) {
        DetailEntity detail = detailService.saveEmpty(userId);
        PrivateDoctorResponseDto responseDto = doctorService.save(userId, detail, dto);
        userAuthService.changeAuthorityById(userId, Authority.ROLE_DOCTOR, response);
        return responseDto;
    }
}
