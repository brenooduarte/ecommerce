package com.ecommerce.api.controller;

import com.ecommerce.domain.dto.form.CategoryDTOForm;
import com.ecommerce.domain.dto.view.CategoryDTOView;
import com.ecommerce.domain.dto.view.ProductDTOView;
import com.ecommerce.domain.repository.CategoryRepository;
import com.ecommerce.domain.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTOView>> getAllCategories() {
        return ResponseEntity.ok().body(categoryService.getAllCategories());
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<List<ProductDTOView>> getAllProductsByCategory(@PathVariable Long categoryId) {
        List<ProductDTOView> productsDTOViews = categoryService.getAllProductsByCategory(categoryId);
        return ResponseEntity.ok().body(productsDTOViews);
    }

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTOForm categoryDTOForm) {
        return ResponseEntity.ok().body(categoryService.createCategory(categoryDTOForm));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTOView> updateCategoryById(@PathVariable Long categoryId, @RequestBody CategoryDTOForm categoryDTOForm) {
        return ResponseEntity.ok().body(categoryService.updateCategoryById(categoryId, categoryDTOForm));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long categoryId) {
        try {
            categoryService.deleteCategoryById(categoryId);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

}
