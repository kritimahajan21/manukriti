package com.manukriti.website.service;

import com.manukriti.website.dto.CategoryRequest;
import com.manukriti.website.dto.ProductRequest;
import com.manukriti.website.model.Category;
import com.manukriti.website.model.Product;

import java.util.List;
import java.util.Set;

public interface ProductService {
    List<Product> getAllProducts();

    Product getProductByName(String productName);

    Product createProduct(ProductRequest productRequest);

    Product updateProduct(Long id, ProductRequest productRequest);

    void deleteProduct(String productName);

}
