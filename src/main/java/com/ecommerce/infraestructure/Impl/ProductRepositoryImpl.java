package com.ecommerce.infraestructure.Impl;

import com.ecommerce.domain.models.Assessment;
import com.ecommerce.domain.models.Product;
import com.ecommerce.domain.models.User;
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
import java.util.NoSuchElementException;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryQueries {


	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public List<Product> listAllActive(List<Product> products) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

		Root<Product> productRoot = criteriaQuery.from(Product.class);

		criteriaQuery.select(productRoot);

		criteriaQuery.where(
				criteriaBuilder.equal(productRoot.get("status"), GlobalConstants.ACTIVE),
				productRoot.in(products));
		productRoot.fetch("assessments", JoinType.LEFT);

		TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();

	}

	@Override
	public Assessment addAssessment(Assessment assessment, Long productId, Long userId) {

//		Product product = productRepository.findById(productId)
//				.orElseThrow(() -> new NoSuchElementException("Product not found"));
//
//		User user = userRepository.findById(userId)
//				.orElseThrow(() -> new NoSuchElementException("User not found"));
//
//		user.addAssessment(assessment);
//		product.addAssessment(assessment);
//		assessmentRepository.save(assessment);



		return null;
	}

}
