package com.aidcompass.specialistdirectory.domain.language;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/languages")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService service;


    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Arrays.stream(Language.values())
                        .collect(Collectors.toMap(Language::getCode, Function.identity()))
                );
    }

    @PostMapping("/set/{code}")
    public ResponseEntity<?> setLanguage(@PathVariable("code") Integer code, HttpServletResponse response) {
        service.setLanguage(code, response);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
