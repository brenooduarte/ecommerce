package com.ecommerce.infraestructure.Impl;

import com.ecommerce.domain.models.Assessment;
import com.ecommerce.domain.models.Product;
import com.ecommerce.infraestructure.query.ProductRepositoryQueries;
import com.ecommerce.utils.GlobalConstants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
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
		.append("JOIN FETCH p.assessments ")
		.append("WHERE p.status = :status");
		
		Query query = entityManager.createNativeQuery(consulta.toString(), Product.class);
		query.setParameter("status", GlobalConstants.ACTIVE);
		
		
		return query.getResultList();
	}

	@Override
	public List<Product> listAllActive2(List<Product> products) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQueryProduct = criteriaBuilder.createQuery(Product.class);

		Root<Product> productRoot = criteriaQueryProduct.from(Product.class);

		criteriaQueryProduct.select(productRoot);

		criteriaQueryProduct.where(
				criteriaBuilder.equal(productRoot.get("status"), GlobalConstants.ACTIVE),
				productRoot.in(products));
		productRoot.fetch("assessments", JoinType.LEFT);

		TypedQuery<Product> query = entityManager.createQuery(criteriaQueryProduct);
		return query.getResultList();

	}


}
