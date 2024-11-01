package com.manukriti.website.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

import lombok.*;

@Data
@Entity
@Table(name = "categories")
@EqualsAndHashCode(exclude = "products")

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(name = "category_name")
    private @NotBlank String categoryName;

    @Column(name = "category_description")
    private String categoryDescription;

//    @Column(name = "image_url")
//    private String imageUrl;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productCategory", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Product> products = new HashSet<Product>();

    public void addProduct(Product product) {
        product.setProductCategory(this);
        products.add(product);
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryDescription='" + categoryDescription + '\'' +
                '}';
    }


}
