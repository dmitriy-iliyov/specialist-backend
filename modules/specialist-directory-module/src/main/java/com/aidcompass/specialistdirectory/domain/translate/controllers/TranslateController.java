//package com.aidcompass.specialistdirectory.domain.translate.controllers;
//
//import com.aidcompass.specialistdirectory.domain.translate.models.dtos.TranslateCreateDto;
//import com.aidcompass.specialistdirectory.domain.translate.models.dtos.TranslateUpdateDto;
//import com.aidcompass.specialistdirectory.domain.translate.services.interfaces.TranslateService;
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Positive;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/admin/v1/types/{type_id}/translates")
//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
//@RequiredArgsConstructor
//public class TranslateController {
//
//    private final TranslateService service;
//
//
//    @PostMapping
//    public ResponseEntity<?> create(@PathVariable("type_id") @NotNull(message = "Type id is required.")
//                                    @Positive(message = "Type id should be positive.") Long typeId,
//                                    @RequestBody @Valid TranslateCreateDto dto) {
//        dto.setTypeId(typeId);
//        service.save(dto);
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .build();
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> update(@PathVariable("type_id") @NotNull(message = "Type id is required.")
//                                    @Positive(message = "Type id should be positive.") Long typeId,
//                                    @PathVariable("id") @NotNull(message = "Id is required.")
//                                    @Positive(message = "Id should be positive.") Long id,
//                                    @RequestBody @Valid TranslateUpdateDto dto) {
//        dto.setTypeId(typeId);
//        dto.setId(id);
//        service.update(dto);
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> delete(@PathVariable("type_id") @NotNull(message = "Type id is required.")
//                                    @Positive(message = "Type id should be positive.") Long typeId,
//                                    @PathVariable("id") @NotNull(message = "Id is required.")
//                                    @Positive(message = "Id should be positive.") Long id) {
//        service.deleteById(id);
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .build();
//    }
//}
