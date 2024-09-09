package com.ecommerce.infraestructure.query;

import com.ecommerce.domain.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryQueries{
	
	 List<Product> findAllProducts(Integer page, Integer size, Long storeId);

     Optional<Product> findByProductId(Long productId, Long storeId);

}
