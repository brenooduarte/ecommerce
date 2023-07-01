package com.ecommerce.infraestructure.query;

import com.ecommerce.domain.models.Assessment;
import com.ecommerce.domain.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface ProductRepositoryQueries{
	 Page<Product> listAllActive(PageRequest pageRequest);
	 Optional<Product> findProductById(Long productId);
	 Page<Assessment> findAllByProductId(Long productId, PageRequest pageRequest);
	 void createAssessment(Assessment assessment, Long productId, Long userId);
	 Page<Product> viewProductByCategory(Long categoryId, PageRequest pageRequest);
}
