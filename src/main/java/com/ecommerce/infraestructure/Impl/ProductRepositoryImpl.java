package com.ecommerce.infraestructure.Impl;

import com.ecommerce.domain.dto.view.ProductDTOView;
import com.ecommerce.domain.dto.view.SearchedProductsDTOView;
import com.ecommerce.domain.exceptions.ProductNotFoundException;
import com.ecommerce.domain.models.Product;
import com.ecommerce.infraestructure.query.ProductRepositoryQueries;
import com.ecommerce.utils.GlobalConstants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryQueries {

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public List<Product> findAllProducts(Integer page, Integer size, Long storeId) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> cq = cb.createQuery(Product.class);

		Root<Product> productRoot = cq.from(Product.class);

		productRoot.fetch("store", JoinType.LEFT);

		cq.select(productRoot);
		cq.where(
				cb.equal(productRoot.get("status"), GlobalConstants.ACTIVE),
				cb.equal(productRoot.get("store").get("id"), storeId)
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

			productRoot.fetch("store", JoinType.LEFT);

			cq.select(productRoot);
			cq.where(
					cb.equal(productRoot.get("id"), productId),
					cb.equal(productRoot.get("status"), GlobalConstants.ACTIVE),
					cb.equal(productRoot.get("store").get("id"), storeId)
			);

			var product = entityManager.createQuery(cq)
					.getSingleResult();

			return Optional.of(product);

		} catch (NoResultException e) {
			throw new ProductNotFoundException();
		}
	}

	@Override
	public SearchedProductsDTOView findAllProductsWithFilters(
			Integer page,
			Integer size,
			Long storeId,
			String productName,
			String brand,
			String priceMin,
			String priceMax
	) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		Root<Product> countRoot = countQuery.from(Product.class);
		countQuery.select(cb.count(countRoot));
		countQuery.where(buildPredicates(countRoot, cb, storeId, productName, brand, priceMin, priceMax).toArray(new Predicate[0]));
		Long totalProducts = entityManager.createQuery(countQuery).getSingleResult();

		CriteriaQuery<Product> cq = cb.createQuery(Product.class);
		Root<Product> productRoot = cq.from(Product.class);
		productRoot.fetch("store", JoinType.LEFT);
		cq.select(productRoot);
		cq.where(buildPredicates(productRoot, cb, storeId, productName, brand, priceMin, priceMax).toArray(new Predicate[0]));

		int firstResult = page * size;
		List<Product> products = entityManager.createQuery(cq)
				.setFirstResult(firstResult)
				.setMaxResults(size)
				.getResultList();

		Set<ProductDTOView> productDTOViews = products.stream()
				.map(product -> {
					ProductDTOView dto = new ProductDTOView();
					dto.setId(product.getId());
					dto.setName(product.getName());
					dto.setBrand(product.getBrand());
					dto.setPrice(product.getPrice());
					dto.setDescription(product.getDescription());
					dto.setImage(product.getImageUrl());
					dto.setHighlight(product.isHighlight());
					dto.setPromotion(product.isPromotion());
					dto.setRelated(product.getRelated());
					dto.setPricePromotion(product.getPromotionPrice());
					return dto;
				})
				.collect(Collectors.toSet());

		SearchedProductsDTOView result = new SearchedProductsDTOView();
		result.setProductDTOViews(productDTOViews);
		result.setQuantityProducts(totalProducts);

		return result;
	}

	private List<Predicate> buildPredicates(
			Root<Product> root,
			CriteriaBuilder cb,
			Long storeId,
			String productName,
			String brand,
			String priceMin,
			String priceMax
	) {
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(cb.equal(root.get("status"), GlobalConstants.ACTIVE));
		predicates.add(cb.equal(root.get("store").get("id"), storeId));

		if (productName != null && !productName.isEmpty()) {
			predicates.add(cb.like(cb.lower(root.get("name")), "%" + productName.toLowerCase() + "%"));
		}

		if (brand != null && !brand.isEmpty()) {
			predicates.add(cb.like(cb.lower(root.get("brand")), "%" + brand.toLowerCase() + "%"));
		}

		if (priceMin != null && !priceMin.isEmpty()) {
			predicates.add(cb.greaterThanOrEqualTo(
					root.get("price"), new BigDecimal(priceMin)));
		}

		if (priceMax != null && !priceMax.isEmpty()) {
			predicates.add(cb.lessThanOrEqualTo(
					root.get("price"), new BigDecimal(priceMax)));
		}

		return predicates;
	}

}
