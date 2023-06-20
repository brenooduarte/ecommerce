package com.ecommerce.domain.service;

import com.ecommerce.domain.dto.view.ProductDTOView;
import com.ecommerce.domain.exceptions.ProductAlreadyExistsException;
import com.ecommerce.domain.models.Assessment;
import com.ecommerce.domain.models.Category;
import com.ecommerce.domain.models.Product;
import com.ecommerce.domain.models.User;
import com.ecommerce.domain.repository.AssessmentRepository;
import com.ecommerce.domain.repository.CategoryRepository;
import com.ecommerce.domain.repository.ProductRepository;
import com.ecommerce.domain.repository.UserRepository;
import org.springframework.beans.BeanUtils;
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
	private AssessmentRepository assessmentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	public Product getById(long id) {
		Optional<Product> userOptional = productRepository.findById(id);
		return userOptional.orElse(null);
	}

	public List<ProductDTOView> listAllActive() {
		List<Product> data = productRepository.listAllActive();
		List<ProductDTOView> listView = new ArrayList<>();
		for (Product product : data) {
			ProductDTOView productView = new ProductDTOView(product);
			listView.add(productView);
		}
		return listView;
	}

	public Page<ProductDTOView> listAllActive2(PageRequest pageRequest) {
		Page<Product> page = productRepository.findAll(pageRequest);
		productRepository.listAllActive2(page.stream().toList());
		return page.map(x -> new ProductDTOView(x));
	}

	public ProductDTOView createProduct(Product product, Long categoryId) throws ProductAlreadyExistsException {

		Product productFound = productRepository.findByName(product.getName());

		if (productFound != null) {
			throw new ProductAlreadyExistsException("Product already exists");
		}

		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new NoSuchElementException("Category not found"));

		product = productRepository.save(product);
		category.addProduct(product);
		categoryRepository.save(category);

		return new ProductDTOView(product);
	}

	public Product updateProduct(Product newProduct) {
		Product product = productRepository.findById(newProduct.getId())
				.orElseThrow(() -> new NoSuchElementException("Product not found"));
		product.setName(newProduct.getName());
		product.setPrice(newProduct.getPrice());

		return productRepository.save(product);
	}

	public void deleteProductById(long id) {
		productRepository.deleteById(id);
	}

    public Assessment addAssessment(Assessment assessment, Long productId, Long userId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new NoSuchElementException("Product not found"));

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new NoSuchElementException("User not found"));

		user.addAssessment(assessment);
		product.addAssessment(assessment);
		assessmentRepository.save(assessment);

		return assessment;
    }

	public void setActivePromotion(Long productId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new NoSuchElementException("Product not found"));

		product.setPromotion(!product.isPromotion());
	}

	public void setActiveProduct(Long productId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new NoSuchElementException("Product not found"));

		product.setPromotion(!product.isStatus());
	}

	public void setCategoryInProduct(Long productId, Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new NoSuchElementException("Category not found"));

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new NoSuchElementException("Product not found"));

		category.addProduct(product);
	}
}
