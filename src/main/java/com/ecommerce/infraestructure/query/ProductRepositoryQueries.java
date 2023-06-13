package com.ecommerce.infraestructure.query;

import java.util.List;

import com.ecommerce.domain.models.Product;

public interface ProductRepositoryQueries{
	
	 List<Product> listAllActive();
}
