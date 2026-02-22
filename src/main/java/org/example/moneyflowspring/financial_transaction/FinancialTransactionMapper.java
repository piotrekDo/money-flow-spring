package org.example.moneyflowspring.financial_transaction;

import org.example.moneyflowspring.category.SubcategoryDto;
import org.example.moneyflowspring.known_merchants.KnownMerchantDto;
import org.example.moneyflowspring.known_merchants.PossibleMerchantDto;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class FinancialTransactionMapper {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    FinancialTransactionEntity fromFileRecord(FinancialTransactionFileRecord fileRecord) {
        String merchantDataClean = fileRecord.getMerchantData().replaceAll("['\"]+", "");
        String titleClean = fileRecord.getTitle().replaceAll("['\"]+", "");
        String normalizedKeywords = normalize(merchantDataClean + " " + titleClean);
        String dedupedKeywords = Arrays.stream(normalizedKeywords.split("\\s+"))
                .distinct()
                .collect(Collectors.joining(" "));
        LocalDate tranDate = LocalDate.parse(fileRecord.getTranDate(), formatter);
        String bankTranNumberClean = fileRecord.getTranNr().replace("'", "").replaceAll("\\s+", "");
        String accountNrClean = fileRecord.getAccountNr().replace("'", "").replaceAll("\\s+", "");

        return new FinancialTransactionEntity(
                null,
                fileRecord.getTranType(),
                tranDate,
                fileRecord.getAmount(),
                null,
                bankTranNumberClean,
                accountNrClean,
                merchantDataClean,
                titleClean,
                dedupedKeywords,
                new ArrayList<>(),
                null,
                true,
                null
        );
    }

    FinancialTransactionDto fromEntity(FinancialTransactionEntity entity) {

        return new FinancialTransactionDto(
                entity.getSystemId(),
                entity.getTranType(),
                entity.getTranDate(),
                entity.getAmount(),
                entity.getComment(),
                entity.getBankTranNr(),
                entity.getAccountNr(),
                entity.getMerchantDataRaw(),
                entity.getTitleRaw(),
                entity.getNormalizedKeywords(),
                entity.getPossibleMerchants().stream().map(PossibleMerchantDto::fromEntity).toList(),
                KnownMerchantDto.fromEntity(entity.getKnownMerchantEntity()),
                entity.isKnownMerchantUnsure(),
                SubcategoryDto.fromEntity(entity.getSubcategoryEntity())
        );
    }

    String normalize(String s) {
        if (s == null) return "";
        s = s.toUpperCase(Locale.ROOT);
        s = s.replace("Ł", "L");
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        s = s.replaceAll("[^A-Z0-9 ]+", " ");
        return s.replaceAll("\\s+", " ").trim();
    }
}
