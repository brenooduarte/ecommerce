package com.ecommerce.domain.service;

import com.ecommerce.domain.dto.form.ProductDTOForm;
import com.ecommerce.domain.dto.view.ProductDTOView;
import com.ecommerce.domain.dto.view.SearchedProductsDTOView;
import com.ecommerce.domain.exceptions.ProductAlreadyExistsException;
import com.ecommerce.domain.models.Category;
import com.ecommerce.domain.models.Product;
import com.ecommerce.domain.repository.CategoryRepository;
import com.ecommerce.domain.repository.ProductRepository;
import com.ecommerce.domain.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	public Optional<Product> findByProductId(Long productId, Long storeId) {
		return productRepository.findByProductId(productId, storeId);
	}

	public List<ProductDTOView> findAllProducts(Integer page, Integer size, Long storeId) {
		List<Product> data = productRepository.findAllProducts(page, size, storeId);
		List<ProductDTOView> listView = new ArrayList<>();
		for (Product product : data) {
			ProductDTOView productView = new ProductDTOView();
			BeanUtils.copyProperties(product, productView);
			listView.add(productView);
		}
		return listView;
	}

	public SearchedProductsDTOView findAllProductsWithFilters(
			Integer page,
			Integer size,
			Long storeId,
			String productName,
			String brand,
			String priceMin,
			String priceMax
	) {
		SearchedProductsDTOView searchedProducts = (SearchedProductsDTOView) productRepository.findAllProductsWithFilters(
				page, size, storeId, productName, brand, priceMin, priceMax
		);

		// Map entities to DTOs if necessary
		Set<ProductDTOView> productDTOViews = searchedProducts.getProductDTOViews().stream()
				.map(product -> {
					ProductDTOView productView = new ProductDTOView();
					BeanUtils.copyProperties(product, productView);
					return productView;
				})
				.collect(Collectors.toSet());

		searchedProducts.setProductDTOViews(productDTOViews);
		return searchedProducts;
	}

	public Product createProduct(ProductDTOForm productDTOForm) throws ProductAlreadyExistsException {

		Product product = new Product();
		BeanUtils.copyProperties(productDTOForm, product, "category_id");

		Product productFound = productRepository.findByName(product.getName());

		if (productFound != null) {
			throw new ProductAlreadyExistsException("Product already exists");
		}

		Category category = categoryRepository.findById(productDTOForm.getCategoryId())
				.orElseThrow(() -> new NoSuchElementException("Category not found"));

		category.addProduct(product);

		return productRepository.save(product);
	}

	public Product updateProduct(Long productId, ProductDTOForm productDTOForm) {
		Optional<Product> productFound = productRepository.findByProductId(productId, productDTOForm.getStoreId());

		if (productFound.isPresent()) {
			BeanUtils.copyProperties(productDTOForm, productFound.get());
			return productRepository.save(productFound.get());
		}

		return new Product();
	}

	public void deleteProductById(long id) {
		productRepository.deleteById(id);
	}

	public void setActivePromotion(Long productId, Long storeId) {
		Product product = productRepository.findByProductId(productId, storeId).get();

		product.setPromotion(!product.isPromotion());
	}

	public void setActiveProduct(Long productId, Long storeId) {
		Product product = productRepository.findByProductId(productId, storeId).get();

		product.setPromotion(!product.isStatus());
	}

	public void setCategoryInProduct(Long productId, Long categoryId, Long storeId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new NoSuchElementException("Category not found"));

		Product product = productRepository.findByProductId(productId, storeId).get();

		category.addProduct(product);
	}
}
