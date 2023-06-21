package com.ecommerce.infraestructure.query;

import com.ecommerce.domain.models.UserAddress;

public interface UserAddressRepositoryQueries {

    UserAddress findByAddressId(Long addressId);

}
