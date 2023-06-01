package com.api.ecommerce.service;

import com.api.ecommerce.exceptions.ProductAlreadyExistsException;
import com.api.ecommerce.exceptions.UserAlreadyExistsException;
import com.api.ecommerce.models.Product;
import com.api.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public Product getById(long id) {
        Optional<Product> userOptional = productRepository.findById(id);
        return userOptional.orElse(null);
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
