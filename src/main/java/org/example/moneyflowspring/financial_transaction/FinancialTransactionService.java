package org.example.moneyflowspring.financial_transaction;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinancialTransactionService {
    private final IngFileReader ingFileReader;

    @PostConstruct
    void init() {
        readFiles();
    }

    void readFiles() {
        List<FinancialTransactionFileRecord> financialTransactionFileRecords = ingFileReader.retrieveNewTransactions();
        financialTransactionFileRecords.forEach(System.out::println);
    }
}
