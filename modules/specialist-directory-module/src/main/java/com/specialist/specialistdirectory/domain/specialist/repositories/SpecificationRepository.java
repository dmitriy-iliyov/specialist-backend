package com.specialist.specialistdirectory.domain.specialist.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationRepository<T> {
    Slice<T> findAll(Specification<T> specification, Pageable pageable);
}
