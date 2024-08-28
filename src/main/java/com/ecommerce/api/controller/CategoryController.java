package com.ecommerce.api.controller;

import com.ecommerce.domain.dto.form.CategoryDTOForm;
import com.ecommerce.domain.models.Product;
import com.ecommerce.domain.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{categoryId}")
    public ResponseEntity<List<Product>> listAllProductsByCategory(@PathVariable Long categoryId) {
        List<Product> products = categoryService.listAllProductsByCategory(categoryId);
        return ResponseEntity.ok().body(products);
    }

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTOForm categoryDTOForm) {
        return ResponseEntity.ok().body(categoryService.createCategory(categoryDTOForm));
    }

}
