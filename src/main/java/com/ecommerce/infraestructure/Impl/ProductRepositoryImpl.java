package com.ecommerce.infraestructure.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecommerce.domain.dto.view.ProductDTOView;
import com.ecommerce.domain.models.Product;
import com.ecommerce.infraestructure.query.ProductRepositoryQueries;
import com.ecommerce.utils.GlobalConstants;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryQueries {
	

	@Autowired
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
