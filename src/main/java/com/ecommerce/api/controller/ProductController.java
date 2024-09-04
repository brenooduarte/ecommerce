package com.ecommerce.api.controller;

import com.ecommerce.domain.dto.form.ProductDTOForm;
import com.ecommerce.domain.dto.view.ProductDTOView;
import com.ecommerce.domain.exceptions.EntityInUseException;
import com.ecommerce.domain.exceptions.ProductAlreadyExistsException;
import com.ecommerce.domain.models.Product;
import com.ecommerce.domain.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTOView> findById(@PathVariable Long productId) {
        Optional<Product> product = productService.findByProductId(productId);
        if (product.isPresent()) {
            ProductDTOView productDTOView = new ProductDTOView();
            BeanUtils.copyProperties(product.get(), productDTOView);
            return new ResponseEntity<>(productDTOView, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<ProductDTOView>> findAllProducts(
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        return new ResponseEntity<>(productService.findAllProducts(page, size), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDTOForm productDTOForm) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(productService.createProduct(productDTOForm));

        } catch (ProductAlreadyExistsException e) {
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
    public ResponseEntity<ProductDTOView> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductDTOForm productDTOForm
    ) {
        ProductDTOView productDTOView = new ProductDTOView();
        Product productUpdated = productService.updateProduct(productId, productDTOForm);
        BeanUtils.copyProperties(productUpdated, productDTOView);

        return new ResponseEntity<>(productDTOView, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> remove(@PathVariable Long productId) {
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
