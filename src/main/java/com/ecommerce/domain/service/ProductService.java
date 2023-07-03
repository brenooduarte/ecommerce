package com.ecommerce.domain.service;

import com.ecommerce.domain.dto.form.ProductDTOForm;
import com.ecommerce.domain.dto.form.ProductDTOFormWithId;
import com.ecommerce.domain.dto.view.ProductDTOView;
import com.ecommerce.domain.exceptions.EntityNotFoundException;
import com.ecommerce.domain.exceptions.ProductAlreadyExistsException;
import com.ecommerce.domain.models.Assessment;
import com.ecommerce.domain.models.Category;
import com.ecommerce.domain.models.Product;
import com.ecommerce.domain.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductOrderRepository productOrderRepository;

	@Autowired
	private AssessmentRepository assessmentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	public Product findProductById(long id) {
		Optional<Product> userOptional = productRepository.findProductById(id);
		return userOptional.orElse(null);
	}

	public Page<ProductDTOView> listAllActive(PageRequest pageRequest) {
		Page<Product> page = productRepository.listAllActive(pageRequest);
		return page.map(ProductDTOView::new);
	}

	public ProductDTOView createProduct(ProductDTOForm productDTOForm) throws ProductAlreadyExistsException {

		Product productFound = productRepository.findByName(productDTOForm.getName());

		if (productFound != null) {
			throw new ProductAlreadyExistsException("Product already exists");
		}

		Product product = new Product(productDTOForm);

		Category category = categoryRepository.findById(productDTOForm.getCategoryId())
				.orElseThrow(() -> new EntityNotFoundException("Category not found"));

		productRepository.createProduct(product, category.getId());
		product = productRepository.findByName(product.getName());

		return new ProductDTOView(product);
	}

	public Page<ProductDTOView> viewProductByCategory(Long categoryId, PageRequest pageRequest) {
		Page<Product> products = productRepository.viewProductByCategory(categoryId, pageRequest);
		return products.map(ProductDTOView::new);
	}

	public Page<Assessment> findAllByProductId(Long productId, PageRequest pageRequest) {
		return productRepository.findAllByProductId(productId, pageRequest);
	}

	public ProductDTOView updateProduct(ProductDTOFormWithId productDTOFormWithId) {

		Optional<Product> product = productRepository.findProductById(productDTOFormWithId.getId());

		if (product.isPresent()) {
			BeanUtils.copyProperties(productDTOFormWithId, product.get(), "id");
			return new ProductDTOView(productRepository.save(product.get()));
		}
		throw new EntityNotFoundException("Product not exists");
	}

    public Assessment addAssessment(Assessment assessment, Long productId, Long userId) {
		productRepository.findProductById(productId)
				.orElseThrow(() -> new EntityNotFoundException("Product not found"));

		return productRepository.createAssessment(assessment, productId, userId);
    }

	public boolean setActivePromotion(Long productId) {
		Product product = productRepository.findProductById(productId)
				.orElseThrow(() -> new EntityNotFoundException("Product not found"));

		product.setPromotion(!product.isPromotion());
		productRepository.save(product);
		return product.isPromotion();
	}

	public boolean setActiveProduct(Long productId) {
		Product product = productRepository.findProductById(productId)
				.orElseThrow(() -> new EntityNotFoundException("Product not found"));

		product.setStatus(!product.isStatus());
		productRepository.save(product);
		return product.isStatus();
	}

	public void setCategoryInProduct(Long productId, Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new EntityNotFoundException("Category not found"));

		Product product = productRepository.findProductById(productId)
				.orElseThrow(() -> new EntityNotFoundException("Product not found"));

		category.addProduct(product);
	}

}
