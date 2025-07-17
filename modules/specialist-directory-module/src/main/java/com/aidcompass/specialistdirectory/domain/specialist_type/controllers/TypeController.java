package com.aidcompass.specialistdirectory.domain.specialist_type.controllers;

import com.aidcompass.specialistdirectory.domain.language.Language;
import com.aidcompass.specialistdirectory.domain.specialist_type.services.interfases.TypeTranslateService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/types")
@RequiredArgsConstructor
public class TypeController {

    private final TypeTranslateService service;


    @GetMapping("/json")
    public ResponseEntity<?> getAllAsJson(HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllApprovedAsJson(getLanguage(request)));
    }

    @GetMapping("/map")
    public ResponseEntity<?> getAllAsMap(HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllApprovedAsMap(getLanguage(request)));
    }

    private Language getLanguage(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getValue().equals("lang"))
                .findFirst()
                .map(cookie -> Language.valueOf(cookie.getValue()))
                .orElse(Language.EN);
    }
}
