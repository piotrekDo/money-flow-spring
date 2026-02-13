package org.example.moneyflowspring.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    SubcategoryEntity subcategoryEntityFromDto(SubcategoryDto dto) {
        return new SubcategoryEntity(
                null,
                dto.getName(),
                dto.getImageUrl(),
                dto.getIconId(),
                dto.getColor(),
                null,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    SubcategoryDto subcategoryDtoFromEntity(SubcategoryEntity entity) {
        return new SubcategoryDto(
                entity.getId(),
                entity.getName(),
                entity.getImageUrl(),
                entity.getIconId(),
                entity.getColor()
        );
    }
}
