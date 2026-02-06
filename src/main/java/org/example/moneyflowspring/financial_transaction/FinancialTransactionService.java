package org.example.moneyflowspring.financial_transaction;

import jakarta.transaction.Transactional;
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
                System.out.println(e.getMessage());
                System.out.println("Duplikat bankTranNr lub naruszenie ograniczenia unikalności");
            }
        }

        return new NewTransactionsFromIngFile(savedTransactions, duplicatedTransactions);
    }


    @Transactional
    void printTransactionsByMerchantId(long merchantId) {
        KnownMerchantEntity knownMerchantEntity = knownMerchantsRepository.findById(merchantId).orElseThrow();
        List<FinancialTransactionEntity> financialTransactionsEntities = knownMerchantEntity.getFinancialTransactionsEntities();
        financialTransactionsEntities.forEach(System.out::println);
    }

}
