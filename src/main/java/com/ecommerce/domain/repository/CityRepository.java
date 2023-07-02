package com.ecommerce.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.domain.models.City;
import com.ecommerce.domain.models.State;

public interface CityRepository extends JpaRepository<City, Long> {

    City findByNameAndState(String name, State state);

}
