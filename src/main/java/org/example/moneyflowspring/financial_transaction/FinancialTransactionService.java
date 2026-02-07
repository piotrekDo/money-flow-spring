package org.example.moneyflowspring.financial_transaction;

import lombok.RequiredArgsConstructor;
import org.example.moneyflowspring.known_merchants.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FinancialTransactionService {
    private final IngFileReader ingFileReader;
    private final FinancialTransactionRepository financialTransactionRepository;
    private final KnownMerchantsRepository knownMerchantsRepository;
    private final FinancialTransactionMapper financialTransactionMapper;
    private final KnownMerchantMatcher knownMerchantMatcher;


    public NewTransactionsFromIngFile retrieveNewTransactionsFormIngFiles() {
        List<FinancialTransactionFileRecord> financialTransactionFileRecords = ingFileReader.retrieveNewTransactionsFromFiles();
        List<FinancialTransactionEntity> savedTransactions = new ArrayList<>();
        List<FinancialTransactionEntity> duplicatedTransactions = new ArrayList<>();


        List<FinancialTransactionEntity> transactionsToSave = financialTransactionFileRecords
                .stream()
                .map(financialTransactionMapper::fromFileRecord)
                .peek(knownMerchantMatcher::matchMerchantForTransaction)
                .toList();

        for (FinancialTransactionEntity transactionToSave : transactionsToSave) {
            try {
                savedTransactions.add(financialTransactionRepository.save(transactionToSave));
            } catch (DataIntegrityViolationException e) {
                duplicatedTransactions.add(transactionToSave);
            }
        }

        return new NewTransactionsFromIngFile(savedTransactions, duplicatedTransactions);
    }
}
