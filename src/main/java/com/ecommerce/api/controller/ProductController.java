package com.ecommerce.api.controller;

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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping
    public ResponseEntity<List<Product>> list() {
        return new ResponseEntity<List<Product>>(productRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> findById(@PathVariable Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }
    
    @GetMapping("/active")
    public ResponseEntity<List<ProductDTOView>> listAllActive() {
        return new ResponseEntity<List<ProductDTOView>>(productService.listAllActive(), HttpStatus.OK);
    }

    @GetMapping("/active2")
    public ResponseEntity<Page<ProductDTOView>> listAllActive2(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductDTOView> list = productService.listAllActive2(pageRequest);
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

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDTOForm productDTOForm) {
        try {
            Product product = new Product();
            BeanUtils.copyProperties(productDTOForm, product, "category_id");

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(productService.createProduct(product, productDTOForm.getCategoryId()));

        } catch (ProductAlreadyExistsException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("{productId}/comments/user/{userId}")
    public ResponseEntity<?> addAssessment(
            @RequestBody Assessment assessment,
            @PathVariable Long productId,
            @PathVariable Long userId) {

        try {

            productService.addAssessment(assessment, productId, userId);
            return ResponseEntity.status(HttpStatus.CREATED).build();

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
    public ResponseEntity<?> update(
            @PathVariable Long productId,
            @RequestBody Product product) {

        try {
            Optional<Product> currentProduct = productRepository.findById(productId);

            if (currentProduct.isPresent()) {
                BeanUtils.copyProperties(product, currentProduct, "id");

                productRepository.save(currentProduct.get());
                return ResponseEntity.ok(currentProduct.get());
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
