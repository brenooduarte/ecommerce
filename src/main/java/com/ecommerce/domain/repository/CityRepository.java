package com.ecommerce.domain.repository;

import com.ecommerce.domain.models.City;
import com.ecommerce.domain.models.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {

    City findByNameAndState(String name, State state);

}
