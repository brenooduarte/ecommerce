package com.ecommerce.domain.repository;

import com.ecommerce.domain.models.ProductOrder;
import com.ecommerce.infraestructure.query.ProductOrderRepositoryQueries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long>, ProductOrderRepositoryQueries {
    void deleteByProductId(Long productId);
}
