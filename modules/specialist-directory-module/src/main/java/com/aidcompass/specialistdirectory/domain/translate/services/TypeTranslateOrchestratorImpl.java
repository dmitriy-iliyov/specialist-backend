package com.aidcompass.specialistdirectory.domain.translate.services;

import com.aidcompass.specialistdirectory.domain.language.Language;
import com.aidcompass.specialistdirectory.domain.translate.services.interfaces.TranslateService;
import com.aidcompass.specialistdirectory.domain.translate.services.interfaces.TypeTranslateOrchestrator;
import com.aidcompass.specialistdirectory.domain.type.models.dtos.ShortTypeResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TypeTranslateOrchestratorImpl implements TypeTranslateOrchestrator {

    private final TranslateService service;


    @Override
    public Map<Long, String> findAllApprovedAsMap(HttpServletRequest request) {
        return service.findAllApprovedAsMap(this.getLanguage(request));
    }

    @Override
    public List<ShortTypeResponseDto> findAllApprovedAsJson(HttpServletRequest request) {
        return service.findAllApprovedAsJson(this.getLanguage(request));
    }

    private Language getLanguage(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getValue().equals("lang"))
                .findFirst()
                .map(cookie -> Language.valueOf(cookie.getValue()))
                .orElse(Language.EN);
    }
}
