package com.ecommerce.infraestructure;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecommerce.domain.models.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryQueries {
	
	private static final boolean PRODUCT_STATUS = true;

	@Autowired
	EntityManager entityManager;
	
	@Override
	public List<Product> listAllActive() {
		StringBuilder consulta = new StringBuilder();
		
		consulta.append("SELECT * ")
		.append("FROM tb_product p ")
		.append("where p.active = :status");
		
		Query query = entityManager.createNativeQuery(consulta.toString(), Product.class);
		query.setParameter("status", PRODUCT_STATUS);
		
		
		return query.getResultList();
	}

}
