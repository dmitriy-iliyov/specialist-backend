package com.specialist.user.models.dtos;

import com.specialist.contracts.user.ProfileType;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record UpdateRequest(
    UUID accountId,
    ProfileType type,
    String rawDto,
    MultipartFile avatar
) { }
