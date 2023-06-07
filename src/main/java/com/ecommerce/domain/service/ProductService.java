package com.ecommerce.domain.service;

import com.ecommerce.domain.models.Product;
import com.ecommerce.domain.exceptions.ProductAlreadyExistsException;
import com.ecommerce.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    
    
//    public List<Product> listAllActive() {
//    	List<Product> returnList = productRepository.listAllActive();
//        return returnList;
//    }
    

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
