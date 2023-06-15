package com.ecommerce.domain.repository;

import com.ecommerce.domain.models.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
}
