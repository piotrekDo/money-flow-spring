package org.example.moneyflowspring.known_merchants;

import lombok.RequiredArgsConstructor;
import org.example.moneyflowspring.category.SubcategoryDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class KnownMerchantMapper {

    KnownMerchantKeywordDto keywordFromEntity(KnownMerchantKeyWordEntity entity) {
        return new KnownMerchantKeywordDto(
                entity.getId(),
                entity.getKeyword(),
                entity.getWeight()
        );
    }

    KnownMerchantDto merchantFromEntity(KnownMerchantEntity entity) {
        List<KnownMerchantKeywordDto> keywordsDto = entity.getKeywords().stream().map(this::keywordFromEntity).toList();
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

    KnownMerchantEntity merchantEntityFromFileRecord(KnownMerchantFileRecord record) {
        return new KnownMerchantEntity(
                null,
                record.getMerchantCode(),
                record.getMerchantName(),
                null,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }
}
