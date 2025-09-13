package com.specialist.user.models.dtos;

import com.specialist.contracts.user.UserType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record CreateRequest(
        UUID accountId,
        UserType type,
        String rawDto,
        MultipartFile avatar,
        HttpServletRequest request,
        HttpServletResponse response
) { }
