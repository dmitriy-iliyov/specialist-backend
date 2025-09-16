package com.specialist.profile.models.dtos;

import com.specialist.contracts.profile.ProfileType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record CreateRequest(
        UUID accountId,
        ProfileType type,
        String rawDto,
        MultipartFile avatar,
        HttpServletRequest request,
        HttpServletResponse response
) { }
