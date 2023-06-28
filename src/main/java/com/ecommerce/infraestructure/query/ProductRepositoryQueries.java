package com.ecommerce.infraestructure.query;

import java.util.List;
import java.util.Optional;

import com.ecommerce.domain.models.Assessment;
import com.ecommerce.domain.models.Product;

public interface ProductRepositoryQueries{
	 List<Product> listAllActive(List<Product> products);
	 Optional<Product> findProductById(Long productId);
	 Assessment addAssessment(Assessment assessment, Long productId, Long userId);
}
