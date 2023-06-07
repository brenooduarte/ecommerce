package com.ecommerce.domain.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.domain.dto.view.ProductDTOView;
import com.ecommerce.domain.exceptions.ProductAlreadyExistsException;
import com.ecommerce.domain.models.Product;
import com.ecommerce.domain.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	public Product getById(long id) {
		Optional<Product> userOptional = productRepository.findById(id);
		return userOptional.orElse(null);
	}

	public List<ProductDTOView> listAllActive() {
		List<Product> data = productRepository.listAllActive();
		List<ProductDTOView> listView = new ArrayList<>();
		for (Product product : data) {
			ProductDTOView productView = new ProductDTOView();
			BeanUtils.copyProperties(product, productView);
			listView.add(productView);
		}
		return listView;
	}

	public Product createProduct(Product product) throws ProductAlreadyExistsException {
		if (product == null) {
			return productRepository.save(product);
		}
		throw new ProductAlreadyExistsException();
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
}
