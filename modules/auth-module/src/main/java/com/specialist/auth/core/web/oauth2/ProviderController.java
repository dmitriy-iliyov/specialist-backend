package com.specialist.auth.core.web.oauth2;


import com.specialist.auth.core.web.oauth2.services.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/info/providers")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService service;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAll());
    }

    @GetMapping("/paths")
    public ResponseEntity<?> getAllPaths() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllPaths());
    }
}
