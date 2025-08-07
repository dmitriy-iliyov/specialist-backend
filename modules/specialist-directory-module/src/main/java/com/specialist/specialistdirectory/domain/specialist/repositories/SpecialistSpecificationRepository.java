package com.specialist.specialistdirectory.domain.specialist.repositories;

import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.specialist.specialistdirectory.utils.SpecificationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SpecialistSpecificationRepository implements SpecificationRepository<SpecialistEntity> {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Slice<SpecialistEntity> findAll(Specification<SpecialistEntity> specification, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SpecialistEntity> q = cb.createQuery(SpecialistEntity.class);
        Root<SpecialistEntity> r = q.from(SpecialistEntity.class);

        Predicate predicate = specification.toPredicate(r, q, cb);
        if (predicate != null) {
            q.where(predicate);
        }

        q.orderBy(QueryUtils.toOrders(pageable.getSort(), r, cb));

        r.fetch("type", JoinType.LEFT);

        TypedQuery<SpecialistEntity> tq = entityManager.createQuery(q);
        tq.setFirstResult((int) pageable.getOffset());
        tq.setMaxResults(pageable.getPageSize() + 1);

        List<SpecialistEntity> result = tq.getResultList();

        boolean hasNext = result.size() > pageable.getPageSize();

        List<SpecialistEntity> content = hasNext ? result.subList(0, pageable.getPageSize()) : result;

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Slice<SpecialistEntity> findAll(Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SpecialistEntity> q = cb.createQuery(SpecialistEntity.class);
        Root<SpecialistEntity> r = q.from(SpecialistEntity.class);

        q.orderBy(QueryUtils.toOrders(pageable.getSort(), r, cb));

        r.fetch("type", JoinType.LEFT);

        TypedQuery<SpecialistEntity> tq = entityManager.createQuery(q);
        tq.setFirstResult((int) pageable.getOffset());
        tq.setMaxResults(pageable.getPageSize() + 1);

        List<SpecialistEntity> result = tq.getResultList();

        boolean hasNext = result.size() > pageable.getPageSize();

        List<SpecialistEntity> content = hasNext ? result.subList(0, pageable.getPageSize()) : result;

        return new SliceImpl<>(content, pageable, hasNext);
    }
}