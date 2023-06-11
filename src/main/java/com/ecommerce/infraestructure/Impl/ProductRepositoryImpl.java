package com.ecommerce.infraestructure.Impl;

import com.ecommerce.domain.models.Product;
import com.ecommerce.infraestructure.query.ProductRepositoryQueries;
import com.ecommerce.utils.GlobalConstants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryQueries {


	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public List<Product> listAllActive() {
		StringBuilder consulta = new StringBuilder();
		
		consulta.append("SELECT * ")
		.append("FROM tb_product p ")
		.append("where p.status = :status");
		
		Query query = entityManager.createNativeQuery(consulta.toString(), Product.class);
		query.setParameter("status", GlobalConstants.ACTIVE);
		
		
		return query.getResultList();
	}

}
