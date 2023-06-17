package com.ecommerce.domain.repository;

import com.ecommerce.domain.enums.UserType;
import com.ecommerce.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByName(String name);

	public User findByEmail(String email);

    public User findByType(UserType userType);
}
