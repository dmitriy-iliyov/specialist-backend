package com.specialist.profile.models.dtos;

import com.specialist.contracts.profile.ProfileType;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record UpdateRequest(
    UUID accountId,
    ProfileType type,
    String rawDto,
    MultipartFile avatar
) { }
