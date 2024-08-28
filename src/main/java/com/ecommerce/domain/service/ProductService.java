package com.ecommerce.domain.service;

import com.ecommerce.domain.dto.form.ProductDTOForm;
import com.ecommerce.domain.dto.view.ProductDTOView;
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

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	public Optional<Product> findByProductId(Long productId) {
		return productRepository.findByProductId(productId);
	}

	public List<ProductDTOView> findAllProducts(Integer page, Integer size) {
		List<Product> data = productRepository.findAllProducts(page, size);
		List<ProductDTOView> listView = new ArrayList<>();
		for (Product product : data) {
			ProductDTOView productView = new ProductDTOView();
			BeanUtils.copyProperties(product, productView);
			listView.add(productView);
		}
		return listView;
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
		Optional<Product> productFound = productRepository.findByProductId(productId);

		if (productFound.isPresent()) {
			BeanUtils.copyProperties(productDTOForm, productFound.get());
			return productRepository.save(productFound.get());
		}

		return new Product();
	}

	public void deleteProductById(long id) {
		productRepository.deleteById(id);
	}

	public void setActivePromotion(Long productId) {
		Product product = productRepository.findByProductId(productId).get();

		product.setPromotion(!product.isPromotion());
	}

	public void setActiveProduct(Long productId) {
		Product product = productRepository.findByProductId(productId).get();

		product.setPromotion(!product.isStatus());
	}

	public void setCategoryInProduct(Long productId, Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new NoSuchElementException("Category not found"));

		Product product = productRepository.findByProductId(productId).get();

		category.addProduct(product);
	}
}
