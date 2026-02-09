package org.example.moneyflowspring.category;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class CategoryWithSubcategoriesDto {
    private Long id;
    private String name;
    private String imageUrl;
    private int iconId;
    private String color;
    private List<SubcategoryDto> subcategories;
}
