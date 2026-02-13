package org.example.moneyflowspring.known_merchants;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class KnownMerchantDto {
    private Long merchantId;
    private String merchantCode;
    private String merchantName;
    private String imageUrl;
    private List<KnownMerchantKeywordDto> keywords;

    public static KnownMerchantDto fromEntity(KnownMerchantEntity entity) {
        if (entity == null) {
            return null;
        }
        List<KnownMerchantKeywordDto> keywordsDto = entity.getKeywords().stream().map(KnownMerchantKeywordDto::fromEntity).toList();
        return new KnownMerchantDto(
                entity.getMerchantId(),
                entity.getMerchantCode(),
                entity.getMerchantName(),
                entity.getImageUrl(),
                keywordsDto
        );
    }
}
