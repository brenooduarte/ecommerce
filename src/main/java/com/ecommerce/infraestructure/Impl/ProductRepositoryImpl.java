package com.ecommerce.infraestructure.Impl;

import com.ecommerce.domain.models.Assessment;
import com.ecommerce.domain.models.Product;
import com.ecommerce.infraestructure.query.ProductRepositoryQueries;
import com.ecommerce.utils.GlobalConstants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

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
	public Product findByName(String name) {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

			Root<Product> productRoot = criteriaQuery.from(Product.class);

			criteriaQuery.select(productRoot);

			criteriaQuery.where(criteriaBuilder.equal(productRoot.get("name"), name));
			productRoot.fetch("assessments", JoinType.LEFT);
			productRoot.fetch("category", JoinType.LEFT);

			return entityManager.createQuery(criteriaQuery).getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
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

}
