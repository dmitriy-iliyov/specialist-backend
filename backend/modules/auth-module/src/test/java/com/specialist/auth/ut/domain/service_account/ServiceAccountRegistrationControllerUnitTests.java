package com.specialist.auth.ut.domain.service_account;

import com.specialist.auth.domain.access_token.models.AccessTokenUserDetails;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.role.Role;
import com.specialist.auth.domain.service_account.AdminServiceAccountController;
import com.specialist.auth.domain.service_account.ServiceAccountService;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceAccountRegistrationControllerUnitTests {

    @Mock
    private ServiceAccountService mockService;

    @InjectMocks
    private AdminServiceAccountController controller;

    @Test
    @DisplayName("UT: create() should save service account and return CREATED response")
    void create_shouldSaveAndReturnCreated() {
        AccessTokenUserDetails principal = mock(AccessTokenUserDetails.class);
        UUID userId = UUID.randomUUID();
        ServiceAccountDto dto = new ServiceAccountDto();
        dto.setRole(Role.ROLE_SERVICE);
        dto.setAuthorities(Set.of(Authority.ACCOUNT_CREATE, Authority.SERVICE_ACCOUNT_MANAGER));

        ServiceAccountResponseDto expectedResponse = new ServiceAccountResponseDto(
                UUID.randomUUID(),
                "name",
                Role.ROLE_SERVICE,
                List.of(Authority.ACCOUNT_CREATE, Authority.SERVICE_ACCOUNT_MANAGER),
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        SecretServiceAccountResponseDto secretServiceAccountResponseDto = new SecretServiceAccountResponseDto("kojgejg", expectedResponse);

        when(principal.getAccountId()).thenReturn(userId);
        when(mockService.save(eq(userId), eq(dto))).thenReturn(secretServiceAccountResponseDto);

        ResponseEntity<?> response = controller.create(principal, dto);

        verify(mockService).save(userId, dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(secretServiceAccountResponseDto, response.getBody());
    }

    @Test
    @DisplayName("UT: update() should set ID, save and return OK response")
    void update_shouldSetIdAndReturnOk() {
        AccessTokenUserDetails principal = mock(AccessTokenUserDetails.class);
        UUID userId = UUID.randomUUID();
        String idStr = UUID.randomUUID().toString();
        UUID id = UUID.fromString(idStr);
        ServiceAccountDto dto = new ServiceAccountDto();
        dto.setRole(Role.ROLE_SERVICE);
        dto.setAuthorities(Set.of(Authority.ACCOUNT_DELETE));

        ServiceAccountResponseDto expectedResponse = new ServiceAccountResponseDto(
                id,
                "name",
                Role.ROLE_SERVICE,
                List.of(Authority.ACCOUNT_DELETE),
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        SecretServiceAccountResponseDto secretServiceAccountResponseDto = new SecretServiceAccountResponseDto("kojgejg", expectedResponse);

        when(principal.getAccountId()).thenReturn(userId);
        when(mockService.save(eq(userId), any(ServiceAccountDto.class))).thenReturn(secretServiceAccountResponseDto);

        ResponseEntity<?> response = controller.update(principal, idStr, dto);

        assertThat(dto.getId(), is(id));
        verify(mockService).save(userId, dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(secretServiceAccountResponseDto, response.getBody());
    }

    @Test
    @DisplayName("UT: getAll() with non-empty list should return OK and data")
    void getAll_nonEmptyList_shouldReturnOk() {
        PageRequest pageRequest = new PageRequest(1, 10, true);

        ServiceAccountResponseDto expectedResponse = new ServiceAccountResponseDto(
                UUID.randomUUID(),
                "name",
                Role.ROLE_SERVICE,
                List.of(Authority.ACCOUNT_DELETE),
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        List<ServiceAccountResponseDto> expectedList = List.of(expectedResponse);

        PageResponse<ServiceAccountResponseDto> expectedPage = new PageResponse<>(expectedList, 1);
        when(mockService.findAll(pageRequest)).thenReturn(expectedPage);

        ResponseEntity<?> response = controller.getAll(pageRequest);

        verify(mockService).findAll(pageRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedPage, response.getBody());
    }

    @Test
    @DisplayName("UT: getAll() with empty list should return OK and empty list")
    void getAll_emptyList_shouldReturnOk() {
        PageRequest pageRequest = new PageRequest(1, 10, true);

        when(mockService.findAll(pageRequest)).thenReturn(new PageResponse<>(Collections.emptyList(), 0));

        ResponseEntity<?> response = controller.getAll(pageRequest);

        verify(mockService).findAll(pageRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new PageResponse<>(Collections.emptyList(), 0), response.getBody());
    }

    @Test
    @DisplayName("UT: delete() should call service.deleteById and return NO_CONTENT")
    void delete_shouldDeleteAndReturnNoContent() {
        AccessTokenUserDetails principal = mock(AccessTokenUserDetails.class);
        UUID id = UUID.randomUUID();
        String idStr = id.toString();

        doNothing().when(mockService).deleteById(id);

        ResponseEntity<?> response = controller.delete(principal, idStr);

        verify(mockService).deleteById(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
}
