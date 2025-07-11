package com.aidcompass.core.security.domain.user.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


@Named("MapperUtils")
@Component
@RequiredArgsConstructor
public class MapperUtils {

    private final PasswordEncoder passwordEncoder;

    @Named("encodePassword")
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Named("formatDate")
    public String formatDate(Instant unformattedDate) {
        LocalDate date = unformattedDate.atZone(ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ROOT);
        return formatter.format(date);
    }
}
