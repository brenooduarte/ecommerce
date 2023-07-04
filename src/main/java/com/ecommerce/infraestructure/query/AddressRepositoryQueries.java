package com.ecommerce.infraestructure.query;

import com.ecommerce.domain.models.Address;

import java.util.List;

public interface AddressRepositoryQueries {

    List<Address> findAll(Long userId);

    Address getAddressType(Long addressTypeId, Long userId);

    Address findByAddressIdAndUserId(Long addressId, Long userId);

    Address createAddress(Address address, Long userId);

}
