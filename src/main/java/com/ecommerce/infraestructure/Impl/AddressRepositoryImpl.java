package com.ecommerce.infraestructure.Impl;

import com.ecommerce.domain.models.Address;
import com.ecommerce.domain.models.User;
import com.ecommerce.infraestructure.query.AddressRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class AddressRepositoryImpl implements AddressRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Address> findAll(Long userId) throws EmptyResultDataAccessException {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);

        Root<Address> addressRoot = criteriaQuery.from(Address.class);

        criteriaQuery.select(addressRoot);

        criteriaQuery.where(criteriaBuilder.equal(addressRoot.get("user").get("id"), userId));
                addressRoot.fetch("city", JoinType.LEFT);
                addressRoot.fetch("user", JoinType.LEFT);

        TypedQuery<Address> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();

    }

    @Override
    public Address getAddressType(Long addressTypeId, Long userId) throws EmptyResultDataAccessException {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);

        Root<Address> addressRoot = criteriaQuery.from(Address.class);
        Root<User> userRoot = criteriaQuery.from(User.class);

        criteriaQuery.select(addressRoot);

        criteriaQuery.where(criteriaBuilder.equal(addressRoot.get("id"), addressTypeId),
                criteriaBuilder.equal(userRoot.get("id"), userId));

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public Address findByAddressIdAndUserId(Long addressId, Long userId) throws EmptyResultDataAccessException {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);

        Root<Address> addressRoot = criteriaQuery.from(Address.class);

        criteriaQuery.select(addressRoot);

        criteriaQuery.where(
                criteriaBuilder.equal(addressRoot.get("user").get("id"), userId),
                criteriaBuilder.equal(addressRoot.get("id"), addressId));
        addressRoot.fetch("user", JoinType.LEFT);
        addressRoot.fetch("city", JoinType.LEFT);

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public Address createAddress(Address address, Long userId) {
        String sql = "INSERT INTO tb_address (cep, street, number, additional, neighborhood, city_id, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Usar o KeyHolder para recuperar a chave primária gerada
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((PreparedStatementCreator) connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, address.getCep());
            ps.setString(2, address.getStreet());
            ps.setString(3, address.getNumber());
            ps.setString(4, address.getAdditional());
            ps.setString(5, address.getNeighborhood());
            ps.setLong(6, address.getCity().getId());
            ps.setLong(7, userId);
            return ps;
        }, keyHolder);

        // Obter o ID gerado
        if (keyHolder.getKey() != null) {
            int addressId = keyHolder.getKey().intValue();
            address.setId((long) addressId);
        }

        return address;
    }
}
