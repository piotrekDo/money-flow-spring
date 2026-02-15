package org.example.moneyflowspring.category;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.moneyflowspring.known_merchants.KnownMerchantsWithTransactionsDto;

import java.util.List;

@RequiredArgsConstructor
@Data
public class SubcategoryWithMerchantsDto {
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
    private List<KnownMerchantsWithTransactionsDto> merchants;
}
