package com.ecommerce.infraestructure.Impl;

import com.ecommerce.domain.models.Assessment;
import com.ecommerce.domain.models.Product;
import com.ecommerce.infraestructure.query.ProductRepositoryQueries;
import com.ecommerce.utils.GlobalConstants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

		criteriaQuery.where(criteriaBuilder.equal(productRoot.get("status"), GlobalConstants.ACTIVE));

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
	public Product findProductById(Long productId) {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

			Root<Product> productRoot = criteriaQuery.from(Product.class);

			criteriaQuery.select(productRoot);

			criteriaQuery.where(
					criteriaBuilder.equal(productRoot.get("status"), GlobalConstants.ACTIVE),
					criteriaBuilder.equal(productRoot.get("id"), productId));

			productRoot.fetch("assessments", JoinType.LEFT);
			productRoot.fetch("category", JoinType.LEFT);

			return entityManager.createQuery(criteriaQuery).getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Set<Product> findAllProductLikeName(String name) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

		Root<Product> productRoot = criteriaQuery.from(Product.class);

		criteriaQuery.select(productRoot);

		criteriaQuery.where(
				criteriaBuilder.equal(productRoot.get("status"), GlobalConstants.ACTIVE),
				criteriaBuilder.like(productRoot.get("name"), "%" + name + "%"));

		productRoot.fetch("assessments", JoinType.LEFT);
		productRoot.fetch("category", JoinType.LEFT);

		return new HashSet<>(entityManager.createQuery(criteriaQuery).getResultList());

	}

	@Override
	public Product findByName(String name) {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

			Root<Product> productRoot = criteriaQuery.from(Product.class);

			criteriaQuery.select(productRoot);

			criteriaQuery.where(
					criteriaBuilder.equal(productRoot.get("status"), GlobalConstants.ACTIVE),
					criteriaBuilder.equal(productRoot.get("name"), name));

			productRoot.fetch("assessments", JoinType.LEFT);
			productRoot.fetch("category", JoinType.LEFT);

			return entityManager.createQuery(criteriaQuery).getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Page<Assessment> findAllAssessmentsByProductId(Long productId, PageRequest pageRequest) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Assessment> criteriaQuery = criteriaBuilder.createQuery(Assessment.class);

		Root<Assessment> assessmentRoot = criteriaQuery.from(Assessment.class);
		Root<Product> productRoot = criteriaQuery.from(Product.class);

		criteriaQuery.select(assessmentRoot);

		criteriaQuery.where(
				criteriaBuilder.equal(productRoot.get("status"), GlobalConstants.ACTIVE),
				criteriaBuilder.equal(productRoot.get("id"), productId));

		int offset = pageRequest.getPageNumber() * pageRequest.getPageSize();
		int limit = pageRequest.getPageSize();

		List<Assessment> assessments = entityManager.createQuery(criteriaQuery)
				.setFirstResult(offset)
				.setMaxResults(limit)
				.getResultList();

		return new PageImpl<>(assessments);
	}

	@Override
	public Assessment createAssessment(Assessment assessment, Long userId, Long productId) {
		String sql = "INSERT INTO tb_assessment (comment, score, user_id, product_id) VALUES (?, ?, ?, ?)";

		// Usar o KeyHolder para recuperar a chave primária gerada
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update((PreparedStatementCreator) connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
			ps.setString(1, assessment.getComment());
			ps.setString(2, assessment.getScore());
			ps.setLong(3, userId);
			ps.setLong(4, productId);
			return ps;
		}, keyHolder);

		// Obter o ID gerado
		if (keyHolder.getKey() != null) {
			int assessmentId = keyHolder.getKey().intValue();
			assessment.setId((long) assessmentId);
		}

		return assessment;
	}

	@Override
	public void createProduct(Product product, Long categoryId) {
		String sql = "INSERT INTO tb_product (name, price, description, image, highlight, promotion, promotion_price, status, category_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate
				.update(sql, product.getName(), product.getPrice(), product.getDescription(), product.getImage(), product.isHighlight(),
						product.isPromotion(), product.getPromotionPrice(), GlobalConstants.ACTIVE, categoryId);
		//todo: retornar o produto
	}

	@Override
	public Page<Product> viewProductByCategory(Long categoryId, PageRequest pageRequest) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

		Root<Product> productRoot = criteriaQuery.from(Product.class);

		criteriaQuery.select(productRoot);

		criteriaQuery.where(
				criteriaBuilder.equal(productRoot.get("status"), GlobalConstants.ACTIVE),
				criteriaBuilder.equal(productRoot.get("category").get("id"), categoryId));

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
	public Page<Product> findProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, PageRequest pageRequest) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> productRoot = criteriaQuery.from(Product.class);

		criteriaQuery.select(productRoot);

		Predicate predicate = criteriaBuilder.conjunction();

		if (minPrice != null)
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(productRoot.get("price"), minPrice));

		if (maxPrice != null)
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(productRoot.get("price"), maxPrice));

		predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(productRoot.get("status"), GlobalConstants.ACTIVE));

		criteriaQuery.where(predicate);

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

}
