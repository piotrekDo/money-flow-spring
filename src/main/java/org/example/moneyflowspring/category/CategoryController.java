package org.example.moneyflowspring.category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/subcategories-no-merchants")
    List<SubcategoryDto> findAllSubcategoriesNoMerchants() {
        return categoryService.findAllSubcategoriesNoMerchants();
    }

    @GetMapping("/categories-all")
    List<CategoryDto> findAllCategories() {
        return categoryService.findAllCategories();
    }
}
