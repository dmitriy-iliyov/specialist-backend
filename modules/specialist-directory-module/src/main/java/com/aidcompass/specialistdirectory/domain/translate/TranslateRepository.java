package com.aidcompass.specialistdirectory.domain.translate;

import com.aidcompass.specialistdirectory.domain.language.Language;
import com.aidcompass.specialistdirectory.domain.translate.models.TranslateEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TranslateRepository extends JpaRepository<TranslateEntity, Long> {

    @EntityGraph(attributePaths = {"type"})
    List<TranslateEntity> findAllByLanguage(Language language);

    List<TranslateEntity> findAllByTypeId(Long typeId);

    List<TranslateEntity> findAllByTypeIdIn(Set<Long> ids);

    Optional<TranslateEntity> findByTypeId(Long typeId);
}
