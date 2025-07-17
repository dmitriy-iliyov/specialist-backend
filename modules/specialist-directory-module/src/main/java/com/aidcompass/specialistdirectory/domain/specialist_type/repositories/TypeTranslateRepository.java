package com.aidcompass.specialistdirectory.domain.specialist_type.repositories;

import com.aidcompass.specialistdirectory.domain.language.Language;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.TypeTranslateEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeTranslateRepository extends JpaRepository<TypeTranslateEntity, Long> {
    @EntityGraph(attributePaths = {"type"})
    List<TypeTranslateEntity> findAllByLanguage(Language language);
}
