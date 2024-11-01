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
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductByName(String productName) {
        return productRepository.findByProductName(productName)
                .orElseThrow(() -> new RuntimeException("Product not found with name: " + productName));
    }

    @Override
    public Product createProduct(ProductRequest productRequest) {
        Optional<Category> categoryOptional = categoryRepository.findByCategoryName(productRequest.getCategoryName());
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            Product product = new Product();
            product.setProductName(productRequest.getProductName());
            product.setPrice(productRequest.getPrice());
            product.setProductDescription(productRequest.getProductDescription());
            log.info("Inserting new product: {} in category: {}", product.getProductName(), category.getCategoryName());
            product.setProductCategory(category);
            category.addProduct(product);
            product = productRepository.save(product);
            categoryRepository.save(category);
            return product;
        } else {
            log.error("Category not found with name: " + productRequest.getCategoryName());
            throw new RuntimeException("Category not found with name: " + productRequest.getCategoryName());
        }
    }

    @Override
    public Product updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        // Update product fields
        if (productRequest.getProductName() != null) {
            product.setProductName(productRequest.getProductName());
        }
        if (productRequest.getPrice() != null) {
            product.setPrice(productRequest.getPrice());
        }
        if (productRequest.getProductDescription() != null) {
            product.setProductDescription(productRequest.getProductDescription());
        }
        if (productRequest.getCategoryName() != null) {
            Optional<Category> newCategory = categoryRepository.findByCategoryName(productRequest.getCategoryName());
            if (newCategory.isPresent()) {
                Category category = newCategory.get();
                Category oldCategory = product.getProductCategory();
                if (oldCategory != null) {
                    oldCategory.getProducts().remove(product);
                    categoryRepository.save(oldCategory);
                }
                product.setProductCategory(category);
                category.addProduct(product);
                categoryRepository.save(category);
            } else {
                log.error("Category not found with name: " + productRequest.getCategoryName() + ". Please create a new category entry.");
                throw new RuntimeException("Category not found with name: " + productRequest.getCategoryName() + ". Please create a new category entry.");
            }


        }
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(String productName) {
        Product product = productRepository.findByProductName(productName)
                .orElseThrow(() -> new RuntimeException("Product not found with name: " + productName));
        productRepository.delete(product); // Delete product (and cascade delete to category if needed)
    }
}

