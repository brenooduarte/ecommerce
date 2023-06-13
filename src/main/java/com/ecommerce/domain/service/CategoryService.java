package com.ecommerce.domain.service;

import com.ecommerce.domain.dto.form.CategoryDTOForm;
import com.ecommerce.domain.dto.view.CategoryDTOView;
import com.ecommerce.domain.models.Category;
import com.ecommerce.domain.models.Product;
import com.ecommerce.domain.repository.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
}
