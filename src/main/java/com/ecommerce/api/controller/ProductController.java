package com.ecommerce.api.controller;

import com.ecommerce.domain.dto.form.ProductDTOForm;
import com.ecommerce.domain.dto.view.ProductDTOView;
import com.ecommerce.domain.dto.view.SearchedProductsDTOView;
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

    @GetMapping("/{productId}/{storeId}")
    public ResponseEntity<ProductDTOView> findById(@PathVariable Long productId, @PathVariable Long storeId) {
        Optional<Product> product = productService.findByProductId(productId, storeId);
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
            @RequestParam Integer size,
            @RequestParam Long storeId
    ) {
        page = page != null ? page : 0;
        size = size != null ? size : 10;

        return new ResponseEntity<>(productService.findAllProducts(page, size, storeId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<SearchedProductsDTOView> findAllProductsWithFilters(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false) Long storeId,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String priceMin,
            @RequestParam(required = false) String priceMax
    ) {
        SearchedProductsDTOView products = productService.findAllProductsWithFilters(
                page,
                size,
                storeId,
                productName,
                brand,
                priceMin,
                priceMax
        );

        return new ResponseEntity<>(products, HttpStatus.OK);
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

    @PatchMapping("/{productId}/category/{categoryId}/stores/{storeId}")
    public ResponseEntity<?> setCategoryInProduct(
            @PathVariable Long productId,
            @PathVariable Long categoryId,
            @PathVariable Long storeId
    ) {
        try {
            productService.setCategoryInProduct(productId, categoryId, storeId);
            return ResponseEntity.ok().build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PatchMapping("/{productId}/active-promotion/stores/{storeId}")
    public void setActivePromotion(
            @PathVariable Long productId,
            @PathVariable Long storeId
    ) {
        productService.setActivePromotion(productId, storeId);
    }

    @PatchMapping("/{productId}/active-product/stores/{storeId}")
    public void setActiveProduct(
            @PathVariable Long productId,
            @PathVariable Long storeId
    ) {
        productService.setActiveProduct(productId, storeId);
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
