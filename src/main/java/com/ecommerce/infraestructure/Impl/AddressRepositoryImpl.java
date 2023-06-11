package com.ecommerce.infraestructure.Impl;

import com.ecommerce.domain.models.Address;
import com.ecommerce.domain.models.UserAddress;
import com.ecommerce.infraestructure.query.AddressRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AddressRepositoryImpl implements AddressRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Address> findAll(Long userId) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);

        Root<Address> addressRoot = criteriaQuery.from(Address.class);
        Root<UserAddress> userAddressRoot = criteriaQuery.from(UserAddress.class);

        criteriaQuery.select(addressRoot);

        criteriaQuery.where(criteriaBuilder.equal(addressRoot.get("id"), userAddressRoot.get("address").get("id")),
                criteriaBuilder.equal(userAddressRoot.get("user").get("id"), userId));

        TypedQuery<Address> query = entityManager.createQuery(criteriaQuery);

        List<Address> addresses = query.getResultList();

        entityManager.close();

        return addresses;

    }
}
