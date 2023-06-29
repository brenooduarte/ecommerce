package com.ecommerce.infraestructure.Impl;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ecommerce.domain.models.Assessment;
import com.ecommerce.domain.models.Product;
import com.ecommerce.infraestructure.query.ProductRepositoryQueries;
import com.ecommerce.utils.GlobalConstants;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryQueries {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Page<Product> listAllActive(PageRequest pageRequest) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

		Root<Product> productRoot = criteriaQuery.from(Product.class);

		criteriaQuery.select(productRoot);

		criteriaQuery.where(
				criteriaBuilder.equal(productRoot.get("status"), GlobalConstants.ACTIVE));
		productRoot.fetch("assessments", JoinType.LEFT);
		productRoot.fetch("category", JoinType.LEFT);

		int offset = pageRequest.getPageNumber() * pageRequest.getPageSize();
		int limit = pageRequest.getPageSize();

		List<Product> query = entityManager.createQuery(criteriaQuery)
				.setFirstResult(offset)
				.setMaxResults(limit)
				.getResultList();

		return new PageImpl<>(query);

	}

	@Override
	public Optional<Product> findProductById(Long productId) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

		Root<Product> productRoot = criteriaQuery.from(Product.class);

		criteriaQuery.select(productRoot);

		criteriaQuery.where(criteriaBuilder.equal(productRoot.get("id"), productId));
		productRoot.fetch("assessments", JoinType.LEFT);
		productRoot.fetch("category", JoinType.LEFT);

		TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);
		return Optional.ofNullable(query.getSingleResult());
	}

	@Override
	public Page<Assessment> findAllByProductId(Long productId, PageRequest pageRequest) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Assessment> criteriaQuery = criteriaBuilder.createQuery(Assessment.class);

		Root<Assessment> assessmentRoot = criteriaQuery.from(Assessment.class);
		Root<Product> productRoot = criteriaQuery.from(Product.class);

		criteriaQuery.select(assessmentRoot);

		criteriaQuery.where(criteriaBuilder.equal(productRoot.get("id"), productId));
		int offset = pageRequest.getPageNumber() * pageRequest.getPageSize();
		int limit = pageRequest.getPageSize();

		List<Assessment> assessments = entityManager.createQuery(criteriaQuery)
				.setFirstResult(offset)
				.setMaxResults(limit)
				.getResultList();

		return new PageImpl<>(assessments);
	}

	@Override
	public void createAssessment(Assessment assessment, Long userId, Long productId) {
		String sql = "INSERT INTO tb_assessment (comment, score, user_id, product_id) VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sql, assessment.getComment(), assessment.getScore(), userId, productId);
	}

}
