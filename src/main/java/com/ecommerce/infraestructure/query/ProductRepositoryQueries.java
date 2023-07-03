package com.ecommerce.infraestructure.query;

import com.ecommerce.domain.models.Assessment;
import com.ecommerce.domain.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.Set;

public interface ProductRepositoryQueries{
	 Page<Product> listAllActive(PageRequest pageRequest);
	 Optional<Product> findProductById(Long productId);
	 Product findByName(String name);
	 Set<Product> findAllProductLikeName(String name);
	 Page<Assessment> findAllByProductId(Long productId, PageRequest pageRequest);
	 Assessment createAssessment(Assessment assessment, Long productId, Long userId);
	 void createProduct(Product product, Long productId);
	 Page<Product> viewProductByCategory(Long categoryId, PageRequest pageRequest);
}
