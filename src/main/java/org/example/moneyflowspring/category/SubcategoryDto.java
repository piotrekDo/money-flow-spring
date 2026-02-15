package org.example.moneyflowspring.category;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SubcategoryDto {
    private Long id;
    private String name;
    private String imageUrl;
    private String icon;
    private String color;
    private Long categoryId;
    private String categoryName;
    private String categoryImageUrl;
    private String categoryIcon;
    private String categoryColor;

    public static SubcategoryDto fromEntity(SubcategoryEntity entity) {
        if (entity == null) {
            return null;
        }

        CategoryEntity category = entity.getCategory();

        return new SubcategoryDto(
                entity.getId(),
                entity.getName(),
                entity.getImageUrl(),
                entity.getIcon(),
                entity.getColor(),
                category != null ? category.getId() : null,
                category != null ? category.getName() : null,
                category != null ? category.getImageUrl() : null,
                category != null ? category.getIcon() : null,
                category != null ? category.getColor() : null
        );
    }
}
