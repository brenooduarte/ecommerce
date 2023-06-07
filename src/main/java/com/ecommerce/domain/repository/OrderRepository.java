package com.ecommerce.domain.repository;

import com.ecommerce.domain.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
