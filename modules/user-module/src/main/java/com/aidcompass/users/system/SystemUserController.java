package com.aidcompass.users.system;

import com.aidcompass.core.general.contracts.enums.ServiceType;
import com.aidcompass.core.general.utils.validation.ValidEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/system/v1/users")
@RequiredArgsConstructor
public class SystemUserController {

    private final ProfileStatusUpdateFacadeImpl facade;


    @PatchMapping("/{user_id}")
    public ResponseEntity<?> updateUserProfileStatus(@PathVariable("user_id") UUID userId,
                                                     @ValidEnum(enumClass = ServiceType.class,
                                                                message = "Unsupported service type!")
                                                     String type) {
        facade.updateProfileStatus(ServiceType.valueOf(type), userId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}