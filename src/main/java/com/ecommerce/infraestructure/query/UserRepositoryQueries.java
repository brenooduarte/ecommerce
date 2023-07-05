package com.ecommerce.infraestructure.query;

import com.ecommerce.domain.models.User;

public interface UserRepositoryQueries {
    User findByEmail(String email);
}
