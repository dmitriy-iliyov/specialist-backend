package com.specialist.specialistdirectory.domain.specialist.repositories;

import com.specialist.specialistdirectory.domain.specialist.models.ActionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialistActionRepository extends CrudRepository<ActionEntity, String> {
}
