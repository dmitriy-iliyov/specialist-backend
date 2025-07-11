package com.aidcompass.users.doctor.repository;

import com.aidcompass.users.doctor.models.DoctorEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DoctorNamesCombination implements Specification<DoctorEntity> {

    private final String firstName;
    private final String secondName;
    private final String lastName;


    public DoctorNamesCombination(String firstName, String secondName, String lastName) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
    }

    @Override
    public Predicate toPredicate(@NonNull Root<DoctorEntity> root, CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>(3);

        if (firstName != null && !firstName.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("firstName")), firstName.toLowerCase() +"%"));
        }

        if (secondName != null && !secondName.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("secondName")), secondName.toLowerCase() +"%"));
        }

        if (lastName != null && !lastName.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("lastName")), lastName.toLowerCase() +"%"));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
