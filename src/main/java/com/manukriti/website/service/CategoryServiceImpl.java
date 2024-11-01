package com.manukriti.website.service;


import com.manukriti.website.dto.CategoryRequest;
import com.manukriti.website.dto.ProductRequest;
import com.manukriti.website.model.Category;
import com.manukriti.website.model.Product;
import com.manukriti.website.repository.CategoryRepository;
import com.manukriti.website.repository.ProductRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Data
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Set<Product> getProductsByCategory(String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new RuntimeException("Category not found with name: " + categoryName));
        return category.getProducts();
    }

    @Override
    public Category createCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        if (categoryRepository.existsByCategoryName(categoryRequest.getCategoryName())) {
            log.error("Category already exists with name: " + categoryRequest.getCategoryName());
            throw new RuntimeException("Category already exists with name: " + categoryRequest.getCategoryName());
        }
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setCategoryDescription(categoryRequest.getCategoryDescription());
        log.info("Inserting new category: {}", category.getCategoryName());
        category = categoryRepository.save(category);
        return category;
    }


    @Override
    public Category updateCategory(Long categoryId, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
        if (categoryRequest.getCategoryName() != null) {
            category.setCategoryName(categoryRequest.getCategoryName());
        }
        if (categoryRequest.getCategoryDescription() != null) {
            category.setCategoryDescription(categoryRequest.getCategoryDescription());
        }
        return categoryRepository.save(category);

    }

    @Override
    public void deleteCategory(String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new RuntimeException("Category not found with name: " + categoryName));
        categoryRepository.delete(category);
    }

}
