package org.example.moneyflowspring.financial_transaction;

import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

@Service
public class FinancialTransactionMapper {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    FinancialTransactionEntity fromFileRecord(FinancialTransactionFileRecord fileRecord) {
        String merchantDataClean = fileRecord.getMerchantData().replaceAll("['\"]+", "");
        String titleClean = fileRecord.getTitle().replaceAll("['\"]+", "");
        String normalizedKeywords = normalize(merchantDataClean + " " + titleClean);
        LocalDate tranDate = LocalDate.parse(fileRecord.getTranDate(), formatter);
        String bankTranNumberClean = fileRecord.getTranNr().replace("'", "").replaceAll("\\s+", "");
        String accountNrClean = fileRecord.getAccountNr().replace("'", "").replaceAll("\\s+", "");

        return new FinancialTransactionEntity(
                null,
                fileRecord.getTranType(),
                tranDate,
                fileRecord.getAmount(),
                bankTranNumberClean,
                accountNrClean,
                merchantDataClean,
                titleClean,
                normalizedKeywords,
                new ArrayList<>(),
                null,
                true);
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
