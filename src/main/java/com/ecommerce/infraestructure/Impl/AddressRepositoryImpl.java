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

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Address> cq = cb.createQuery(Address.class);

        Root<Address> addressRoot = cq.from(Address.class);
        Root<UserAddress> userAddressRoot = cq.from(UserAddress.class);

        cq.select(addressRoot);

        cq.where(
                cb.equal(addressRoot.get("id"), userAddressRoot.get("address").get("id")),
                cb.equal(userAddressRoot.get("user").get("id"), userId)
        );

        TypedQuery<Address> query = entityManager.createQuery(cq);

        List<Address> addresses = query.getResultList();

        entityManager.close();

        return addresses;

    }
}
