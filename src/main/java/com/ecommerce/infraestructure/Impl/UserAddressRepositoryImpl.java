package com.ecommerce.infraestructure.Impl;

import com.ecommerce.domain.models.UserAddress;
import com.ecommerce.infraestructure.query.UserAddressRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class UserAddressRepositoryImpl implements UserAddressRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserAddress findByAddressId(Long addressId) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserAddress> criteriaQuery = criteriaBuilder.createQuery(UserAddress.class);

        Root<UserAddress> userAddressRoot = criteriaQuery.from(UserAddress.class);

        criteriaQuery.select(userAddressRoot);
        criteriaQuery.where(criteriaBuilder.equal(userAddressRoot.get("address").get("id"), addressId));
        userAddressRoot.fetch("user", JoinType.LEFT);
        userAddressRoot.fetch("address", JoinType.LEFT);

        TypedQuery<UserAddress> query = entityManager.createQuery(criteriaQuery);

        return query.getSingleResult();
    }

}