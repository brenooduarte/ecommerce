package com.ecommerce.domain.service;

import com.ecommerce.domain.dto.form.CategoryDTOForm;
import com.ecommerce.domain.dto.view.CategoryDTOView;
import com.ecommerce.domain.dto.view.ProductDTOView;
import com.ecommerce.domain.models.Category;
import com.ecommerce.domain.models.Product;
import com.ecommerce.domain.repository.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTOView createCategory(CategoryDTOForm categoryDTOForm) {
        Category category = categoryRepository.findByName(categoryDTOForm.getName());

        if (category == null) {
            category = new Category(categoryDTOForm.getImage(), categoryDTOForm.getName());
            categoryRepository.save(category);
        }

        CategoryDTOView categoryDTOView = new CategoryDTOView();
        BeanUtils.copyProperties(category, categoryDTOView);
        return categoryDTOView;
    }

    public List<Product> listAllProductsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found"));
        return category.getProducts();
    }

    public List<CategoryDTOView> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(category -> {
            CategoryDTOView categoryDTOView = new CategoryDTOView();
            BeanUtils.copyProperties(category, categoryDTOView);
            return  categoryDTOView;
        }).collect(Collectors.toList());
    }

    public List<ProductDTOView> getAllProductsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found"));

        return category.getProducts().stream()
                .map(product -> {
                    return new ProductDTOView(product);
                })
                .collect(Collectors.toList());
    }

    public CategoryDTOView updateCategoryById(Long categoryId, CategoryDTOForm categoryDTOForm) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found"));

        BeanUtils.copyProperties(categoryDTOForm, category);

        CategoryDTOView categoryDTOView = new CategoryDTOView();
        BeanUtils.copyProperties(category, categoryDTOView);
        return categoryDTOView;
    }

    public void deleteCategoryById(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

}
