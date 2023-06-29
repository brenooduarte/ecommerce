package com.ecommerce.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.persistence.NoResultException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.domain.dto.form.ProductDTOForm;
import com.ecommerce.domain.dto.view.ProductDTOView;
import com.ecommerce.domain.exceptions.EntityInUseException;
import com.ecommerce.domain.exceptions.ProductAlreadyExistsException;
import com.ecommerce.domain.models.Assessment;
import com.ecommerce.domain.models.Product;
import com.ecommerce.domain.repository.ProductRepository;
import com.ecommerce.domain.repository.WishlistRepository;
import com.ecommerce.domain.service.ProductService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/products")
@Tag(name = "Product", description = "Controller de Product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private WishlistRepository wishlistRepository;

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTOView> findById(@PathVariable Long productId) {
        try {
            return ResponseEntity.ok(new ProductDTOView(productService.findProductById(productId)));
        } catch (NoResultException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/active")
    public ResponseEntity<Page<ProductDTOView>> listAllActive(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductDTOView> list = productService.listAllActive(pageRequest);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/wishlist/user/{userId}")
    public ResponseEntity<List<Product>> listAllProductsInWishlist(
            @PathVariable Long userId) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(wishlistRepository.listAllProductsInWishlist(userId));

        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.noContent()
                    .build();
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTOView>> viewProductByCategory(@PathVariable Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        List<ProductDTOView> productDTOViews = products.stream()
                .map(ProductDTOView::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOViews);
        //todo: melhorar performace
    }

    @GetMapping("{productId}/assessments")
    public Page<Assessment> findAllByProductId(
            @PathVariable Long productId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return productService.findAllByProductId(productId, pageRequest);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDTOForm productDTOForm) {
        try {
            ProductDTOView createdProduct = productService.createProduct(productDTOForm);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createdProduct);
        } catch (ProductAlreadyExistsException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("{productId}/assessments/user/{userId}")
    public ResponseEntity<?> addAssessment(
            @RequestBody Assessment assessment,
            @PathVariable Long productId,
            @PathVariable Long userId
    ) {

        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(productService.addAssessment(assessment, productId, userId));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PatchMapping("/{productId}/category/{categoryId}")
    public ResponseEntity<?> setCategoryInProduct(
            @PathVariable Long productId,
            @PathVariable Long categoryId) {

        try {
            productService.setCategoryInProduct(productId, categoryId);
            return ResponseEntity.ok().build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PatchMapping("/{productId}/active-promotion")
    public void setActivePromotion(@PathVariable Long productId) {
        productService.setActivePromotion(productId);
    }

    @PatchMapping("/{productId}/active-product")
    public void setActiveProduct(@PathVariable Long productId) {
        productService.setActiveProduct(productId);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long productId,
            @RequestBody Product product) {

        try {
            Optional<Product> currentProduct = productRepository.findProductById(productId);

            if (currentProduct.isPresent()) {
                BeanUtils.copyProperties(product, currentProduct, "id");

                productRepository.save(currentProduct.get());
                return ResponseEntity.ok(new ProductDTOView(currentProduct.get()));
            }

            return ResponseEntity.notFound().build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long productId) {
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.noContent().build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();

        } catch (EntityInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

}
