package com.manukriti.website.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {

    private String categoryName;

    private String categoryDescription;

}