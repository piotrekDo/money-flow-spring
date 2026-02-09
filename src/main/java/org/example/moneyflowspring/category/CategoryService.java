package org.example.moneyflowspring.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryMapper categoryMapper;

    public SubcategoryDto createSubcategory(SubcategoryDto dto) {
        SubcategoryEntity entityToSave = categoryMapper.subcategoryEntityFromDto(dto);
        SubcategoryEntity entitySaved = subcategoryRepository.save(entityToSave);
        return categoryMapper.subcategoryDtoFromEntity(entitySaved);
    }
}
