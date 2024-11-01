package com.manukriti.website.controller;

import com.manukriti.website.dto.CategoryRequest;
import com.manukriti.website.model.Category;
import com.manukriti.website.model.Product;
import com.manukriti.website.service.CategoryService;
import com.manukriti.website.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/categories")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Get all Categories")
    @GetMapping("search/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories;
        try {
            categories = categoryService.getAllCategories();
        } catch (Exception e) {
            log.error("Error getting categories", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Get all products in a Category")
    @GetMapping("/search/{categoryName}/products")
    public ResponseEntity<Set<Product>> getProductsByCategory(@PathVariable String categoryName) {
        Set<Product> products;
        try {
            products = categoryService.getProductsByCategory(categoryName);
        } catch (Exception e) {
            log.error("Error getting products", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Create a new Category")
    @PostMapping("/create")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category created successfully",
                    content = {@Content(
                            schema = @Schema(implementation = Category.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Category> createCategory(CategoryRequest categoryRequest) {
        Category category;
        try {
            category = categoryService.createCategory(categoryRequest);
        } catch (Exception e) {
            log.error("Error creating category", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(category);
    }

    @Operation(summary = "Update Category")
    @PutMapping("/update/{categoryId}")
    public ResponseEntity<Category> updateCategory(@RequestParam Long categoryId, @RequestBody CategoryRequest categoryRequest) {
        Category category;
        try {
            category = categoryService.updateCategory(categoryId, categoryRequest);
        } catch (Exception e) {
            log.error("Error updating category", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(category);
    }

    @Operation(summary = "Delete Category")
    @DeleteMapping("/delete/{categoryName}")
    public ResponseEntity<Void> deleteCategory(@RequestParam String categoryName) {
        try {
            categoryService.deleteCategory(categoryName);
        } catch (Exception e) {
            log.error("Error deleting category", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.noContent().build();
    }


}
