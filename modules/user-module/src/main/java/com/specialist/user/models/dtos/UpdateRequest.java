package com.specialist.user.models.dtos;

import com.specialist.contracts.user.UserType;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record UpdateRequest(
    UUID accountId,
    UserType type,
    String rawDto,
    MultipartFile avatar
) { }
