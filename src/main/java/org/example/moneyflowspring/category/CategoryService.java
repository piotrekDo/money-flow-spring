package org.example.moneyflowspring.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryMapper categoryMapper;

    List<SubcategoryDto> findAllSubcategoriesNoMerchants() {
        return subcategoryRepository
                .findAll()
                .stream()
                .map(SubcategoryDto::fromEntity)
                .toList();
    }

    List<CategoryDto> findAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryDtoFromEntity)
                .toList();
    }

    public CategoryDto createCategory(CategoryDto categoryDto) {
        CategoryEntity categoryToSave = categoryMapper.categoryEntityFromDto(categoryDto);
        CategoryEntity categoryEntitySaved = categoryRepository.save(categoryToSave);
        return categoryMapper.categoryDtoFromEntity(categoryEntitySaved);
    }

    public SubcategoryDto createSubcategory(SubcategoryDto dto) {
        SubcategoryEntity entityToSave = categoryMapper.subcategoryEntityFromDto(dto);
        SubcategoryEntity entitySaved = subcategoryRepository.save(entityToSave);
        return SubcategoryDto.fromEntity(entitySaved);
    }


}
