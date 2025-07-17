package com.aidcompass.specialistdirectory.domain.specialist_type.services.interfases;

import com.aidcompass.specialistdirectory.domain.language.Language;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.ShortTypeDto;

import java.util.List;
import java.util.Map;

public interface TypeTranslateService {
    List<ShortTypeDto> findAllApprovedAsJson(Language language);

    Map<Long, String> findAllApprovedAsMap(Language language);
}
