package org.example.moneyflowspring.category;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SubcategoryDto {
    private Long id;
    private String name;
    private String imageUrl;
    private int iconId;
    private String color;

    public static SubcategoryDto fromEntity(SubcategoryEntity entity) {
        if (entity == null) {
            return null;
        }
        return new SubcategoryDto(
                entity.getId(),
                entity.getName(),
                entity.getImageUrl(),
                entity.getIconId(),
                entity.getColor()
        );
    }
}
