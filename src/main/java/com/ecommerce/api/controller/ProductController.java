package com.ecommerce.api.controller;

import com.ecommerce.domain.dto.form.AssessmentDTOForm;
import com.ecommerce.domain.dto.form.ProductDTOForm;
import com.ecommerce.domain.dto.form.ProductDTOFormWithId;
import com.ecommerce.domain.dto.view.ProductDTOView;
import com.ecommerce.domain.exceptions.ProductAlreadyExistsException;
import com.ecommerce.domain.models.Assessment;
import com.ecommerce.domain.repository.ProductRepository;
import com.ecommerce.domain.repository.WishlistRepository;
import com.ecommerce.domain.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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

    @GetMapping("/search")
    public ResponseEntity<Set<ProductDTOView>> findAllProductLikeName(
            @RequestParam(value = "name") String productName) {
        try {
            if (productName != null)
                return ResponseEntity.ok(productService.findAllProductLikeName(productName));
            else
                return ResponseEntity.badRequest().build();
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

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Page<ProductDTOView>> viewProductByCategory(
            @PathVariable Long categoryId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductDTOView> productDTOViews = productService.viewProductByCategory(categoryId, pageRequest);

        return ResponseEntity.ok(productDTOViews);
    }

    @GetMapping("{productId}/assessments")
    public Page<Assessment> findAllByProductId(
            @PathVariable Long productId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return productService.findAllByProductId(productId, pageRequest);
    }

    @PostMapping
    public ResponseEntity<ProductDTOView> createProduct(@RequestBody ProductDTOForm productDTOForm) {
        try {
            ProductDTOView createdProduct = productService.createProduct(productDTOForm);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createdProduct);

        } catch (ProductAlreadyExistsException e) {
            return ResponseEntity.badRequest().build();
        }
        //todo: melhorar performace
    }

    @PostMapping("{productId}/assessments/user/{userId}")
    public ResponseEntity<?> addAssessment(
            @RequestBody AssessmentDTOForm assessmentDTOForm,
            @PathVariable Long productId,
            @PathVariable Long userId
    ) {

        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(productService.addAssessment(assessmentDTOForm, productId, userId));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("{productId}/user/{userId}")
    public ResponseEntity<?> addProductInWishlist(
            @PathVariable Long productId,
            @PathVariable Long userId
    ) {
        wishlistRepository.addProductInWishlist(productId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{productId}/category/{categoryId}")
    public ResponseEntity<?> setCategoryInProduct(
            @PathVariable Long productId,
            @PathVariable Long categoryId) {

        try {
            productService.setCategoryInProduct(productId, categoryId);
            return ResponseEntity.ok().build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{productId}/active-promotion")
    public ResponseEntity<Boolean> setActivePromotion(@PathVariable Long productId) {
        try {
            return ResponseEntity.ok(productService.setActivePromotion(productId));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PatchMapping("/{productId}/active-product")
    public ResponseEntity<Boolean> setActiveProduct(@PathVariable Long productId) {
        try {
            return ResponseEntity.ok(productService.setActiveProduct(productId));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/edit")
    public ResponseEntity<ProductDTOView> updateProduct(
            @RequestBody ProductDTOFormWithId productDTOFormWithId
    ) {
        try {
            return ResponseEntity.ok(productService.updateProduct(productDTOFormWithId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
