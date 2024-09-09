package com.ecommerce.infraestructure.Impl;

import com.ecommerce.domain.exceptions.ProductNotFoundException;
import com.ecommerce.domain.models.Product;
import com.ecommerce.infraestructure.query.ProductRepositoryQueries;
import com.ecommerce.utils.GlobalConstants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryQueries {

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public List<Product> findAllProducts(Integer page, Integer size, Long storeId) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> cq = cb.createQuery(Product.class);

		Root<Product> productRoot = cq.from(Product.class);

		cq.select(productRoot);
		cq.where(
				cb.equal(productRoot.get("status"), GlobalConstants.ACTIVE),
				cb.equal(productRoot.get("store_id"), storeId)
		);

		int firstResult = page * size;

		return entityManager.createQuery(cq)
				.setFirstResult(firstResult)
				.setMaxResults(size)
				.getResultList();
	}

	@Override
	public Optional<Product> findByProductId(Long productId, Long storeId) {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Product> cq = cb.createQuery(Product.class);

			Root<Product> productRoot = cq.from(Product.class);

			cq.select(productRoot);
			cq.where(
					cb.equal(productRoot.get("id"), productId),
					cb.equal(productRoot.get("status"), GlobalConstants.ACTIVE),
					cb.equal(productRoot.get("store_id"), storeId)
			);

			var product = entityManager.createQuery(cq)
					.getSingleResult();

			return Optional.of(product);

		} catch (NoResultException e) {
			throw new ProductNotFoundException();
		}
	}

}
