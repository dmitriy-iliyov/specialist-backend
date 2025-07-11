package com.aidcompass.users.jurist.repository;

import com.aidcompass.users.jurist.models.JuristEntity;
import com.aidcompass.users.jurist.specialization.models.JuristSpecializationEntity;
import com.aidcompass.users.jurist.specialization.models.JuristTypeEntity;
import com.aidcompass.users.profile_status.models.ProfileStatus;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class JuristSpecifications {

    public static Specification<JuristEntity> hasType(JuristTypeEntity typeEntity) {
        return (r, q, cb) -> typeEntity == null ? null: cb.equal(r.get("typeEntity"), typeEntity);
    }

    public static Specification<JuristEntity> hasSpecialization(JuristSpecializationEntity specializationEntity) {
        return (r, q, cb) -> {
            if (specializationEntity == null) return null;

            Join<Object, Object> join = r.join("specializations");
            return cb.equal(join, specializationEntity);
        };
    }

    public static Specification<JuristEntity> hasApproval() {
        return (r, q, cb) -> cb.isTrue(r.get("isApproved"));
    }

    public static Specification<JuristEntity> hasNotApproval() {
        return (r, q, cb) -> cb.isFalse(r.get("isApproved"));
    }

    public static Specification<JuristEntity> hasCompletedProfile() {
        return (r, q, cb) -> cb.equal(r.get("profileStatusEntity").get("profileStatus"), ProfileStatus.COMPLETE);
    }

    public static Specification<JuristEntity> hasFirstName(String firstName) {
        return (r, q, cb) -> firstName == null ? null : cb.like(cb.lower(r.get("firstName")), firstName.toLowerCase() + "%");
    }

    public static Specification<JuristEntity> hasSecondName(String secondName) {
        return (r, q, cb) -> secondName == null ? null : cb.like(cb.lower(r.get("secondName")), secondName.toLowerCase() + "%");
    }

    public static Specification<JuristEntity> hasLastName(String lastName) {
        return (r, q, cb) -> lastName == null ? null : cb.like(cb.lower(r.get("lastName")), lastName.toLowerCase() + "%");
    }
}
