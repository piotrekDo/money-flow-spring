package org.example.moneyflowspring.financial_transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.moneyflowspring.category.SubcategoryDto;
import org.example.moneyflowspring.known_merchants.KnownMerchantDto;
import org.example.moneyflowspring.known_merchants.PossibleMerchantDto;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Data
public class FinancialTransactionDto {
    private Long systemId;
    private TranType tranType;
    private LocalDate tranDate;
    private Double amount;
    private String bankTranNr;
    private String accountNr;
    private String merchantDataRaw;
    private String titleRaw;
    private String normalizedKeywords;
    private List<PossibleMerchantDto> possibleMerchants;
    private KnownMerchantDto knownMerchant;
    private boolean knownMerchantUnsure;
    private SubcategoryDto subcategoryDto;
}
