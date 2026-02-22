package org.example.moneyflowspring.known_merchants;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.moneyflowspring.category.SubcategoryDto;
import org.example.moneyflowspring.financial_transaction.FinancialTransactionDto;
import org.example.moneyflowspring.financial_transaction.FinancialTransactionEntity;

import java.util.List;

@AllArgsConstructor
@Data
public class KnownMerchantsWithTransactionsDto {
    private Long merchantId;
    private String merchantCode;
    private String merchantName;
    private String imageUrl;
    private List<KnownMerchantKeywordDto> keywords;
    private List<SubcategoryDto> subcategories;
    private List<FinancialTransactionDto> transactions;

    public static KnownMerchantsWithTransactionsDto fromEntity(KnownMerchantEntity entity) {
        if (entity == null) {
            return null;
        }
        List<KnownMerchantKeywordDto> keywordsDto = entity.getKeywords().stream().map(KnownMerchantKeywordDto::fromEntity).toList();
        List<SubcategoryDto> subcategories = entity.getSubcategories().stream().map(SubcategoryDto::fromEntity).toList();
        List<FinancialTransactionDto> transactions = entity.getFinancialTransactionsEntities().stream().map(FinancialTransactionDto::fromEntity).toList();
        return new KnownMerchantsWithTransactionsDto(
                entity.getMerchantId(),
                entity.getMerchantCode(),
                entity.getMerchantName(),
                entity.getImageUrl(),
                keywordsDto,
                subcategories,
                transactions
        );
    }

    public static KnownMerchantsWithTransactionsDto fromEntity(KnownMerchantEntity entity, List<FinancialTransactionEntity> transactionsEntities) {
        if (entity == null) {
            return null;
        }
        List<KnownMerchantKeywordDto> keywordsDto = entity.getKeywords().stream().map(KnownMerchantKeywordDto::fromEntity).toList();
        List<SubcategoryDto> subcategories = entity.getSubcategories().stream().map(SubcategoryDto::fromEntity).toList();
        List<FinancialTransactionDto> transactions = transactionsEntities.stream().map(FinancialTransactionDto::fromEntity).toList();
        return new KnownMerchantsWithTransactionsDto(
                entity.getMerchantId(),
                entity.getMerchantCode(),
                entity.getMerchantName(),
                entity.getImageUrl(),
                keywordsDto,
                subcategories,
                transactions
        );
    }
}
