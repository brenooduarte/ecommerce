package com.ecommerce.infraestructure.query;

import com.ecommerce.domain.models.Address;

import java.util.List;

public interface AddressRepositoryQueries {

    List<Address> findAll(Long userId);

}
