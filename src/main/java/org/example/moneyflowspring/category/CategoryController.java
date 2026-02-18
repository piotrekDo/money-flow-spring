package org.example.moneyflowspring.category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/subcategory-by-id")
    SubcategoryWithMerchantsDto findSubcategoryWithMerchantsById(@RequestParam Long id) {
        return categoryService.findSubcategoryWithMerchantsById(id);
    }

    @GetMapping("/subcategories-no-merchants")
    List<SubcategoryDto> findAllSubcategoriesNoMerchants() {
        return categoryService.findAllSubcategoriesNoMerchants();
    }

    @GetMapping("/categories-all")
    List<CategoryDto> findAllCategories() {
        return categoryService.findAllCategories();
    }

    @PostMapping("/change-subcategory-cat")
    SubcategoryDto changeSubcategoryCategory(
            @RequestParam Long subCategoryId,
            @RequestParam Long categoryId
    ) {
        return categoryService.changeSubcategoryCategory(subCategoryId, categoryId);
    }
}
