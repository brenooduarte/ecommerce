package com.ecommerce.domain.repository;

import com.ecommerce.domain.models.Address;
import com.ecommerce.infraestructure.query.AddressRepositoryQueries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long>, AddressRepositoryQueries {
}