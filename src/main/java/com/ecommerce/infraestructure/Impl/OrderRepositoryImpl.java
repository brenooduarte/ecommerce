package com.ecommerce.infraestructure.Impl;

import com.ecommerce.domain.models.Order;
import com.ecommerce.infraestructure.query.OrderRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Order> listAll(Long userId) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);

        Root<Order> orderRoot = criteriaQuery.from(Order.class);

        criteriaQuery.select(orderRoot);

        criteriaQuery.where(criteriaBuilder.equal(orderRoot.get("customer").get("id"), userId));

        TypedQuery<Order> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();

    }
}
