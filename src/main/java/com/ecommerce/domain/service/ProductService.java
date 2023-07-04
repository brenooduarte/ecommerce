package com.ecommerce.domain.service;

import com.ecommerce.domain.dto.form.AssessmentDTOForm;
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

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

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

	public ProductDTOView findProductById(Long productId) throws EntityNotFoundException {
		Product product = productRepository.findProductById(productId);
		if (product == null)
			throw new EntityNotFoundException("Product not exists");

		return new ProductDTOView(product);
	}

	public Page<ProductDTOView> listAllActive(PageRequest pageRequest) {
		Page<Product> page = productRepository.listAllActive(pageRequest);
		return page.map(ProductDTOView::new);
	}

	public ProductDTOView createProduct(ProductDTOForm productDTOForm) throws ProductAlreadyExistsException {

		Product productFound = productRepository.findByName(productDTOForm.getName());

		if (productFound != null)
			throw new ProductAlreadyExistsException("Product already exists");

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

	public Page<Assessment> findAllAssessmentsByProductId(Long productId, PageRequest pageRequest) {
		return productRepository.findAllAssessmentsByProductId(productId, pageRequest);
	}

	public ProductDTOView updateProduct(ProductDTOFormWithId productDTOFormWithId) {

		Product product = productRepository.findProductById(productDTOFormWithId.getId());

		if (product != null) {
			BeanUtils.copyProperties(productDTOFormWithId, product, "id");
			return new ProductDTOView(productRepository.save(product));
		}
		throw new EntityNotFoundException("Product not exists");
	}

    public Assessment addAssessment(AssessmentDTOForm assessmentDTOForm, Long productId, Long userId) {
		Product product = productRepository.findProductById(productId);
		if (product == null)
			throw new EntityNotFoundException("Product not exists");

		Assessment assessment = new Assessment(assessmentDTOForm);
		return productRepository.createAssessment(assessment, productId, userId);
    }

	public boolean setActivePromotion(Long productId) {
		Product product = productRepository.findProductById(productId);
		if (product == null)
			throw new EntityNotFoundException("Product not exists");

		product.setPromotion(!product.isPromotion());
		productRepository.save(product);
		return product.isPromotion();
	}

	public boolean setActiveProduct(Long productId) {
		Product product = productRepository.findProductById(productId);
		if (product == null)
			throw new EntityNotFoundException("Product not exists");

		product.setStatus(!product.isStatus());
		productRepository.save(product);
		return product.isStatus();
	}

	public void setCategoryInProduct(Long productId, Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new EntityNotFoundException("Category not found"));

		Product product = productRepository.findProductById(productId);
		if (product == null)
			throw new EntityNotFoundException("Product not exists");

		category.addProduct(product);
	}

	public Set<ProductDTOView> findAllProductLikeName(String productName) {
		Set<Product> products = productRepository.findAllProductLikeName(productName);
		return products.stream().map(ProductDTOView::new).collect(Collectors.toSet());
	}

	public Page<Product> findProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, PageRequest pageRequest) {
		return productRepository.findProductsByPriceRange(minPrice, maxPrice, pageRequest);
	}
}
