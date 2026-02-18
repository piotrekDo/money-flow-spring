package org.example.moneyflowspring.category;

import lombok.RequiredArgsConstructor;
import org.example.moneyflowspring.known_merchants.KnownMerchantDto;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryMapper categoryMapper;

    SubcategoryWithMerchantsDto findSubcategoryWithMerchantsById(Long id){
        SubcategoryEntity subcategoryEntity = subcategoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Subcategory with id " + id + " not found"));
        CategoryEntity category = subcategoryEntity.getCategory();
        List<KnownMerchantDto> merchants = subcategoryEntity.getMerchants().stream().map(KnownMerchantDto::fromEntity).toList();
        return new SubcategoryWithMerchantsDto(
                subcategoryEntity.getId(),
                subcategoryEntity.getName(),
                subcategoryEntity.getImageUrl(),
                subcategoryEntity.getIcon(),
                subcategoryEntity.getColor(),
                category != null ? category.getId() : null,
                category != null ? category.getName() : null,
                category != null ? category.getImageUrl() : null,
                category != null ? category.getIcon() : null,
                category != null? category.getColor() : null,
                merchants
        );
    }

    List<SubcategoryDto> findAllSubcategoriesNoMerchants() {
        return subcategoryRepository
                .findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(SubcategoryDto::fromEntity)
                .toList();
    }

    List<CategoryDto> findAllCategories() {
        return categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(categoryMapper::categoryDtoFromEntity)
                .toList();
    }

    SubcategoryDto changeSubcategoryCategory(long subCategoryId, long categoryId) {
        SubcategoryEntity subcategoryEntity = subcategoryRepository.findById(subCategoryId).orElseThrow(() -> new NoSuchElementException("Subcategory with id: " + subCategoryId + " does not exist"));
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> new NoSuchElementException("Category with id: " + categoryId + " does not exist"));
        subcategoryEntity.setCategory(categoryEntity);
        categoryEntity.getCategoryEntities().add(subcategoryEntity);
        SubcategoryEntity subcategoryUpdated = subcategoryRepository.save(subcategoryEntity);
        return SubcategoryDto.fromEntity(subcategoryUpdated);
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
