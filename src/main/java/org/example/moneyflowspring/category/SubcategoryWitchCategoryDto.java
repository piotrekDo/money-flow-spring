package org.example.moneyflowspring.category;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SubcategoryWitchCategoryDto {
    private Long id;
    private String name;
    private String imageUrl;
    private int iconId;
    private String color;
    private CategoryDto category;
}
