package com.specialist.auth.domain.account.controllers;

import com.specialist.auth.domain.account.models.enums.DisableReason;
import com.specialist.auth.domain.account.models.enums.LockReason;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.role.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AdminAccountInfoControllerUnitTests {

    @InjectMocks
    private AdminAccountInfoController controller;

    @Test
    @DisplayName("UT: getRoles() should return all roles")
    void getRoles_shouldReturnAllRoles() {
        ResponseEntity<?> result = controller.getRoles();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertArrayEquals(Role.values(), (Role[]) result.getBody());
    }

    @Test
    @DisplayName("UT: getAuthorities() should return all authorities")
    void getAuthorities_shouldReturnAllAuthorities() {
        ResponseEntity<?> result = controller.getAuthorities();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertArrayEquals(Authority.values(), (Authority[]) result.getBody());
    }

    @Test
    @DisplayName("UT: getDisableReasonTypes() should return all disable reasons")
    void getDisableReasonTypes_shouldReturnAllDisableReasons() {
        ResponseEntity<?> result = controller.getDisableReasonTypes();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertArrayEquals(DisableReason.values(), (DisableReason[]) result.getBody());
    }

    @Test
    @DisplayName("UT: getLockReasonTypes() should return all lock reasons")
    void getLockReasonTypes_shouldReturnAllLockReasons() {
        ResponseEntity<?> result = controller.getLockReasonTypes();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertArrayEquals(LockReason.values(), (LockReason[]) result.getBody());
    }
}
