package com.specialist.specialistdirectory.domain.specialist.repositories;

import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public class SpecialistSpecification {

    public static Specification<SpecialistEntity> filterByCity(String city) {
        return (r, q, cb) -> city == null ? null : cb.equal(cb.lower(r.get("cityTitle")), city.toLowerCase());
    }

    public static Specification<SpecialistEntity> filterByCityCode(String cityCode) {
        return (r, q, cb) -> cityCode == null ? null : cb.equal(cb.lower(r.get("cityCode")), cityCode.toLowerCase());
    }

    public static Specification<SpecialistEntity> filterByType(Long typeId) {
        return (r, q, cb) -> typeId == null ? null : cb.equal(r.get("type").get("id"), typeId);
    }

    public static Specification<SpecialistEntity> filerByMinRating(Integer minRating) {
        return (r, q, cb) -> minRating == null ? null : cb.greaterThanOrEqualTo(r.get("rating"), minRating);
    }

    public static Specification<SpecialistEntity> filterByMaxRating(Integer maxRating) {
        return (r, q, cb) -> maxRating == null ? null : cb.lessThanOrEqualTo(r.get("rating"), maxRating);
    }

    public static Specification<SpecialistEntity> filterByCreatorId(UUID creatorId) {
        return (r, q, cb) -> creatorId == null ? null : cb.equal(r.get("creatorId"), creatorId);
    }

    public static Specification<SpecialistEntity> filterByFirstName(String firstName) {
        return (r, q, cb) -> firstName == null ? null : cb.like(cb.lower(r.get("firstName")), firstName.toLowerCase());
    }

    public static Specification<SpecialistEntity> filterBySecondName(String secondName) {
        return (r, q, cb) -> secondName == null ? null : cb.like(cb.lower(r.get("secondName")), secondName.toLowerCase());
    }

    public static Specification<SpecialistEntity> filterByLastName(String lastName) {
        return (r, q, cb) -> lastName == null ? null : cb.like(cb.lower(r.get("lastName")), lastName.toLowerCase());
    }

    public static Specification<SpecialistEntity> filterByIdIn(List<UUID> ids) {
        return (r, q, cb) -> ids.isEmpty() ? null : r.get("id").in(ids);
    }
}
