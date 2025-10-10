package com.specialist.auth.ut.domain.account.controllers;

import com.specialist.auth.domain.account.controllers.AdminAccountInfoController;
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

import static org.junit.Assert.*;

@ExtendWith(MockitoExtension.class)
public class AdminAccountInfoControllerUnitTests {

    @InjectMocks
    AdminAccountInfoController controller;

    @Test
    @DisplayName("UT: getRoles() should return 200 and all enum values")
    public void getRoles_shouldReturn200AndEnumValues() {
        ResponseEntity<?> response = controller.getRoles();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Role[] expected = Role.values();
        Role[] actual = (Role[]) response.getBody();

        assertArrayEquals(expected, actual);
    }

    @Test
    @DisplayName("UT: getAuthorities() should return 200 and all enum values")
    public void getAuthorities_shouldReturn200AndEnumValues() {
        ResponseEntity<?> response = controller.getAuthorities();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Authority[] expected = Authority.values();
        Authority[] actual = (Authority[]) response.getBody();

        assertArrayEquals(expected, actual);
    }

    @Test
    @DisplayName("UT: getUnableReasonTypes() should return 200 and all enum values")
    public void getDisableReasonTypes_shouldReturn200AndEnumValues() {
        ResponseEntity<?> response = controller.getDisableReasonTypes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        DisableReason[] expected = DisableReason.values();
        DisableReason[] actual = (DisableReason[]) response.getBody();

        assertArrayEquals(expected, actual);
    }

    @Test
    @DisplayName("UT: getLockReasonTypes() should return 200 and all enum values")
    public void getLockReasonTypes_shouldReturn200AndEnumValues() {
        ResponseEntity<?> response = controller.getLockReasonTypes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        LockReason[] expected = LockReason.values();
        LockReason[] actual = (LockReason[]) response.getBody();

        assertArrayEquals(expected, actual);
    }
}
