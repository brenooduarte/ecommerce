package com.ecommerce.infraestructure;

import java.util.List;

import com.ecommerce.domain.models.Product;

public interface ProductRepositoryQueries{
	
	 List<Product> listAllActive();
}
