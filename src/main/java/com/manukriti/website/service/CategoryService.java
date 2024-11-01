package com.manukriti.website.service;

import com.manukriti.website.dto.CategoryRequest;
import com.manukriti.website.dto.ProductRequest;
import com.manukriti.website.model.Category;
import com.manukriti.website.model.Product;

import java.util.List;
import java.util.Set;

public interface CategoryService {

    List<Category> getAllCategories();

    Set<Product> getProductsByCategory(String categoryName);

    Category createCategory(CategoryRequest categoryRequest);

    Category updateCategory(Long categoryId, CategoryRequest categoryRequest);

    void deleteCategory(String categoryName);
}
