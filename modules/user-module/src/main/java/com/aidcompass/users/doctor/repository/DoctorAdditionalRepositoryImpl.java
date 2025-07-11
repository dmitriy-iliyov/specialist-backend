package com.aidcompass.users.doctor.repository;

import com.aidcompass.users.doctor.models.DoctorEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class DoctorAdditionalRepositoryImpl implements DoctorAdditionalRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Page<DoctorEntity> findAllByNamesCombination(Specification<DoctorEntity> spec, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<DoctorEntity> query = cb.createQuery(DoctorEntity.class);
        Root<DoctorEntity> root = query.from(DoctorEntity.class);

        Predicate mainSpecPredicate = spec != null ? spec.toPredicate(root, query, cb) : cb.conjunction();

        Predicate isApproved = cb.isTrue(root.get("isApproved"));
        Predicate profileComplete = cb.equal(
                root.get("profileStatusEntity").get("profileStatus"), "COMPLETE"
        );

        Predicate finalPredicate = cb.and(mainSpecPredicate, isApproved, profileComplete);

        query.select(root).where(finalPredicate);

        TypedQuery<DoctorEntity> typedQuery = entityManager.createQuery(query);

        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<DoctorEntity> resultList = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<DoctorEntity> countRoot = countQuery.from(DoctorEntity.class);

        Predicate countSpecPredicate = spec != null ? spec.toPredicate(countRoot, countQuery, cb) : cb.conjunction();
        Predicate countIsApproved = cb.isTrue(countRoot.get("isApproved"));
        Predicate countProfileComplete = cb.equal(
                countRoot.get("profileStatusEntity").get("profileStatus"), "COMPLETE"
        );
        Predicate finalCountPredicate = cb.and(countSpecPredicate, countIsApproved, countProfileComplete);

        countQuery.select(cb.count(countRoot)).where(finalCountPredicate);
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        return new PageImpl<>(resultList, pageable, total);
    }

    @Override
    public Page<DoctorEntity> findAllUnapprovedByNamesCombination(Specification<DoctorEntity> spec, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<DoctorEntity> query = cb.createQuery(DoctorEntity.class);
        Root<DoctorEntity> root = query.from(DoctorEntity.class);

        Predicate mainSpecPredicate = spec != null ? spec.toPredicate(root, query, cb) : cb.conjunction();

        Predicate isApproved = cb.isFalse(root.get("isApproved"));

        Predicate finalPredicate = cb.and(mainSpecPredicate, isApproved);

        query.select(root).where(finalPredicate);

        TypedQuery<DoctorEntity> typedQuery = entityManager.createQuery(query);

        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<DoctorEntity> resultList = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<DoctorEntity> countRoot = countQuery.from(DoctorEntity.class);

        Predicate countSpecPredicate = spec != null ? spec.toPredicate(countRoot, countQuery, cb) : cb.conjunction();
        Predicate countIsApproved = cb.isFalse(root.get("isApproved"));

        Predicate finalCountPredicate = cb.and(countSpecPredicate, countIsApproved);

        countQuery.select(cb.count(countRoot)).where(finalCountPredicate);
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        return new PageImpl<>(resultList, pageable, total);
    }
}