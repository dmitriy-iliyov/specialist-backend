package com.aidcompass.users.customer.repository;

import com.aidcompass.users.customer.models.CustomerEntity;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecifications {

    public static Specification<CustomerEntity> hasFirstName(String firstName) {
        return (r, q, cb) -> firstName == null ? null : cb.like(cb.lower(r.get("firstName")), firstName.toLowerCase() + "%");
    }

    public static Specification<CustomerEntity> hasSecondName(String secondName) {
        return (r, q, cb) -> secondName == null ? null : cb.like(cb.lower(r.get("secondName")), secondName.toLowerCase() + "%");
    }

    public static Specification<CustomerEntity> hasLastName(String lastName) {
        return (r, q, cb) -> lastName == null ? null : cb.like(cb.lower(r.get("lastName")), lastName.toLowerCase() + "%");
    }
}
