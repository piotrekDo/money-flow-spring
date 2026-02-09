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
    private int iconId;
    private String color;
    private CategoryDto category;
    private List<KnownMerchantsWithTransactionsDto> merchants;
}
