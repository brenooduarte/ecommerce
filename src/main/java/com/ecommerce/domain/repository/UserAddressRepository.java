package com.ecommerce.domain.repository;

import com.ecommerce.domain.models.UserAddress;
import com.ecommerce.infraestructure.query.UserAddressRepositoryQueries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long>, UserAddressRepositoryQueries {
}
