package org.example.moneyflowspring.known_merchants;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.moneyflowspring.financial_transaction.FinancialTransactionDto;

import java.util.List;

@AllArgsConstructor
@Data
public class KnownMerchantsWithTransactionsDto {
    private Long merchantId;
    private String merchantCode;
    private String merchantName;
    private String imageUrl;
   private List<KnownMerchantKeywordDto> keywords;
    private List<FinancialTransactionDto> transactions;
}
