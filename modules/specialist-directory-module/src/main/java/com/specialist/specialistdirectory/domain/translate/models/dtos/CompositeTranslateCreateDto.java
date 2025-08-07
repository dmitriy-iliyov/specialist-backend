package com.specialist.specialistdirectory.domain.translate.models.dtos;

import com.specialist.specialistdirectory.domain.language.Language;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public record CompositeTranslateCreateDto(
        Language language,

        @NotBlank(message = "Translate is required.")
        String translate
) {
        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                CompositeTranslateCreateDto that = (CompositeTranslateCreateDto) o;
                return Objects.equals(language, that.language()) &&
                        Objects.equals(translate, that.translate());
        }

        @Override
        public int hashCode() {
                int hashCode = Objects.hash(language);
                return 31 * hashCode + Objects.hash(translate);
        }
}