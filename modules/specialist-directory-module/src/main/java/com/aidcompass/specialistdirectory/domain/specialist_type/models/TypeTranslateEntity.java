package com.aidcompass.specialistdirectory.domain.specialist_type.models;

import com.aidcompass.specialistdirectory.domain.language.Language;
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
public class TypeTranslateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private TypeEntity type;

    @Column(nullable = false)
    private String translate;

    @Convert(converter = LanguageConverter.class)
    @Column(nullable = false)
    private Language language;
}
