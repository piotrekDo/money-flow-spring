package org.example.moneyflowspring.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    CategoryEntity categoryEntityFromDto(CategoryDto dto) {
        return new CategoryEntity(
                null,
                dto.getName(),
                dto.getImageUrl(),
                dto.getIcon(),
                dto.getColor(),
                new ArrayList<>()
        );
    }

    CategoryDto categoryDtoFromEntity(CategoryEntity entity) {
        List<SubcategoryDto> subcategories = entity.getCategoryEntities().stream().map(SubcategoryDto::fromEntity).toList();
        return new CategoryDto(
                entity.getId(),
                entity.getName(),
                entity.getImageUrl(),
                entity.getIcon(),
                entity.getColor(),
                subcategories
        );
    }

    SubcategoryEntity subcategoryEntityFromDto(SubcategoryDto dto) {
        return new SubcategoryEntity(
                null,
                dto.getName(),
                dto.getImageUrl(),
                dto.getIcon(),
                dto.getColor(),
                null,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }
}
