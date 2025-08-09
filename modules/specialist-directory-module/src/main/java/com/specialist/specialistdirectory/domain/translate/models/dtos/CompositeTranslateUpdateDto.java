package com.specialist.specialistdirectory.domain.translate.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.specialist.specialistdirectory.domain.language.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Data
public class CompositeTranslateUpdateDto {
        @NotNull(message = "Id is required.")
        @PositiveOrZero(message = "Id should be positive or zero.")
        private final Long id;

        @JsonIgnore
        private Long typeId;

        private final Language language;

        @NotBlank(message = "Translate is required.")
        private final String translate;


        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                CompositeTranslateUpdateDto that = (CompositeTranslateUpdateDto) o;
                return Objects.equals(language, that.getLanguage()) &&
                        Objects.equals(translate, that.getTranslate());
        }

        @Override
        public int hashCode() {
                int hashCode = Objects.hash(language);
                return 31 * hashCode + Objects.hash(translate);
        }
}