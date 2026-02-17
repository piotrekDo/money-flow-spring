package org.example.moneyflowspring.known_merchants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.moneyflowspring.category.SubcategoryDto;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class KnownMerchantDto {
    private Long merchantId;
    private String merchantCode;
    private String merchantName;
    private String imageUrl;
    private List<KnownMerchantKeywordDto> keywords;
    private List<SubcategoryDto> subcategories;

    public static KnownMerchantDto fromEntity(KnownMerchantEntity entity) {
        if (entity == null) {
            return null;
        }
        List<KnownMerchantKeywordDto> keywordsDto = entity.getKeywords().stream().map(KnownMerchantKeywordDto::fromEntity).toList();
        List<SubcategoryDto> subcategories = entity.getSubcategories().stream().map(SubcategoryDto::fromEntity).toList();
        return new KnownMerchantDto(
                entity.getMerchantId(),
                entity.getMerchantCode(),
                entity.getMerchantName(),
                entity.getImageUrl(),
                keywordsDto,
                subcategories
        );
    }
}
