package com.ecommerce.domain.repository;

import com.ecommerce.domain.enums.UserType;
import com.ecommerce.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUserType(UserType userType);
}
