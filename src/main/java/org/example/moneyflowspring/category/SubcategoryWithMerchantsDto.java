package org.example.moneyflowspring.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.moneyflowspring.known_merchants.KnownMerchantDto;

import java.util.List;

@AllArgsConstructor
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
    private List<KnownMerchantDto> merchants;
}
