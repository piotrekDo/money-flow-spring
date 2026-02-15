package org.example.moneyflowspring.category;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class CategoryDto {
    private Long id;
    private String name;
    private String imageUrl;
    private String icon;
    private String color;
    private List<SubcategoryDto> subcategories;
}
