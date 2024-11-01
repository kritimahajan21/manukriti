package com.manukriti.website.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductRequest {
    private String productName;

    private String productDescription;

    private Double price;

    private String categoryName;
}