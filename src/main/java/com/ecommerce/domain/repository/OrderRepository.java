package com.ecommerce.domain.repository;

import com.ecommerce.domain.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.customer.id = :userId")
    List<Order> findAllByUserId(Long userId);

}
