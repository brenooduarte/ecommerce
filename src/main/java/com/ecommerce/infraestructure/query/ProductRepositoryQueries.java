package com.ecommerce.infraestructure.query;

import com.ecommerce.domain.models.Assessment;
import com.ecommerce.domain.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryQueries{
	 List<Product> listAllActive(List<Product> products);
	 Optional<Product> findProductById(Long productId);
	 Page<Assessment> findAllByProductId(Long productId, PageRequest pageRequest);
	 Assessment addAssessment(Assessment assessment, Long productId, Long userId);
}
