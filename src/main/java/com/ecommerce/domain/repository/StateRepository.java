package com.ecommerce.domain.repository;

import com.ecommerce.domain.models.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {

    State findByName(String name);

}
