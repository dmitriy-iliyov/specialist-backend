package com.aidcompass.specialistdirectory.domain.translate.models;

import com.aidcompass.specialistdirectory.domain.language.Language;
import com.aidcompass.specialistdirectory.domain.type.models.TypeEntity;
import com.aidcompass.specialistdirectory.domain.language.LanguageConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "specialist_type_translates")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "type")
public class TranslateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false, updatable = false)
    private TypeEntity type;

    @Convert(converter = LanguageConverter.class)
    @Column(nullable = false)
    private Language language;

    @Column(nullable = false)
    private String translate;
}
