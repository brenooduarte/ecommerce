package com.ecommerce.api.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.domain.dto.form.CategoryDTOForm;
import com.ecommerce.domain.dto.view.CategoryDTOView;
import com.ecommerce.domain.dto.view.ProductDTOView;
import com.ecommerce.domain.repository.CategoryRepository;
import com.ecommerce.domain.service.CategoryService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/categories")
@Tag(name = "Category", description = "Controller de Category")
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

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTOView> getCategoryById(@PathVariable Long categoryId) {
        CategoryDTOView categoryDTOView = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok().body(categoryDTOView);
    }

    @PostMapping
    public ResponseEntity<?> addCategory(
            @Valid @RequestBody CategoryDTOForm categoryDTOForm) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.createCategory(categoryDTOForm));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTOView> updateCategoryById(
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryDTOForm categoryDTOForm) {

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
