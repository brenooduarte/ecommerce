package com.ecommerce.domain.service;

import com.ecommerce.domain.dto.form.ProductDTOForm;
import com.ecommerce.domain.dto.view.ProductDTOView;
import com.ecommerce.domain.exceptions.EntityNotFoundException;
import com.ecommerce.domain.exceptions.ProductAlreadyExistsException;
import com.ecommerce.domain.models.Assessment;
import com.ecommerce.domain.models.Category;
import com.ecommerce.domain.models.Product;
import com.ecommerce.domain.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
		Product product = new Product(productDTOForm);

		Category category = categoryRepository.findById(productDTOForm.getCategoryId())
				.orElseThrow(() -> new NoSuchElementException("Category not found"));

		Product productFound = productRepository.findByName(product.getName());

		if (productFound != null) {
			throw new ProductAlreadyExistsException("Product already exists");
		}

		product = productRepository.save(product);
		category.addProduct(product);
		categoryRepository.save(category);

		return new ProductDTOView(product);
	}

	public List<ProductDTOView> viewProductByCategory(Long categoryId) {
		List<Product> products = productRepository.findByCategoryId(categoryId);
		List<ProductDTOView> productDTOViews = new ArrayList<>();

		for (Product product : products) {
			productDTOViews.add(new ProductDTOView(product));
		}

		return productDTOViews;
	}

	public Page<Assessment> findAllByProductId(Long productId, PageRequest pageRequest) {
		return productRepository.findAllByProductId(productId, pageRequest);
	}

	public Product updateProduct(Product newProduct) {
		Product product = productRepository.findProductById(newProduct.getId())
				.orElseThrow(() -> new NoSuchElementException("Product not found"));
		product.setName(newProduct.getName());
		product.setPrice(newProduct.getPrice());

		return productRepository.save(product);
	}

	public void deleteProductById(Long productId) {
		productOrderRepository.deleteByProductId(productId);
		productRepository.deleteById(productId);
	}

    public Assessment addAssessment(Assessment assessment, Long productId, Long userId) {
		productRepository.findProductById(productId)
				.orElseThrow(() -> new EntityNotFoundException("Product not found"));

		productRepository.createAssessment(assessment, productId, userId);

		return assessment;
    }

	public void setActivePromotion(Long productId) {
		Product product = productRepository.findProductById(productId)
				.orElseThrow(() -> new NoSuchElementException("Product not found"));

		product.setPromotion(!product.isPromotion());
	}

	public void setActiveProduct(Long productId) {
		Product product = productRepository.findProductById(productId)
				.orElseThrow(() -> new NoSuchElementException("Product not found"));

		product.setPromotion(!product.isStatus());
	}

	public void setCategoryInProduct(Long productId, Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new NoSuchElementException("Category not found"));

		Product product = productRepository.findProductById(productId)
				.orElseThrow(() -> new NoSuchElementException("Product not found"));

		category.addProduct(product);
	}

}
