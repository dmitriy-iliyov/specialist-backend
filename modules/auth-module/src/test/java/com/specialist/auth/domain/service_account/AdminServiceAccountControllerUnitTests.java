package com.specialist.auth.domain.service_account;

import com.specialist.auth.domain.access_token.models.AccessTokenUserDetails;
import com.specialist.auth.domain.service_account.models.SecretServiceAccountResponseDto;
import com.specialist.auth.domain.service_account.models.ServiceAccountDto;
import com.specialist.auth.domain.service_account.models.ServiceAccountResponseDto;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceAccountControllerUnitTests {

    @Mock
    private ServiceAccountService service;

    @InjectMocks
    private AdminServiceAccountController controller;

    @Test
    @DisplayName("UT: create() should call service and return CREATED")
    void create_shouldCallService() {
        UUID adminId = UUID.randomUUID();
        AccessTokenUserDetails principal = mock(AccessTokenUserDetails.class);
        when(principal.getAccountId()).thenReturn(adminId);
        
        ServiceAccountDto dto = new ServiceAccountDto();
        SecretServiceAccountResponseDto responseDto = new SecretServiceAccountResponseDto("secret", mock(ServiceAccountResponseDto.class));

        when(service.save(adminId, dto)).thenReturn(responseDto);

        ResponseEntity<?> result = controller.create(principal, dto);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(responseDto, result.getBody());
        verify(service).save(adminId, dto);
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("UT: update() should set id, call service and return OK")
    void update_shouldCallService() {
        UUID adminId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        AccessTokenUserDetails principal = mock(AccessTokenUserDetails.class);
        when(principal.getAccountId()).thenReturn(adminId);
        
        ServiceAccountDto dto = new ServiceAccountDto();
        SecretServiceAccountResponseDto responseDto = new SecretServiceAccountResponseDto("secret", mock(ServiceAccountResponseDto.class));

        when(service.save(adminId, dto)).thenReturn(responseDto);

        ResponseEntity<?> result = controller.update(principal, accountId.toString(), dto);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseDto, result.getBody());
        assertEquals(accountId, dto.getId());
        verify(service).save(adminId, dto);
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("UT: getAll() should call service and return OK")
    void getAll_shouldCallService() {
        PageRequest request = new PageRequest(0, 10, true);
        PageResponse<ServiceAccountResponseDto> pageResponse = new PageResponse<>(Collections.emptyList(), 0);

        when(service.findAll(request)).thenReturn(pageResponse);

        ResponseEntity<?> result = controller.getAll(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(pageResponse, result.getBody());
        verify(service).findAll(request);
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("UT: delete() should call service and return NO_CONTENT")
    void delete_shouldCallService() {
        UUID id = UUID.randomUUID();
        AccessTokenUserDetails principal = mock(AccessTokenUserDetails.class);

        ResponseEntity<?> result = controller.delete(principal, id.toString());

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(service).deleteById(id);
        verifyNoMoreInteractions(service);
    }
}
