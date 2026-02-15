package org.example.moneyflowspring.financial_transaction;

import lombok.RequiredArgsConstructor;
import org.example.moneyflowspring.known_merchants.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FinancialTransactionService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final IngFileReader ingFileReader;
    private final FinancialTransactionRepository financialTransactionRepository;
    private final KnownMerchantsRepository knownMerchantsRepository;
    private final FinancialTransactionMapper financialTransactionMapper;
    private final KnownMerchantMatcher knownMerchantMatcher;

    FinancialTransactionDto recalculateTransactionPossibleMerchantsBySystemId(Long systemId) {
        FinancialTransactionEntity transactionEntity = financialTransactionRepository.findById(systemId).orElseThrow();
        FinancialTransactionEntity entityUpdated = financialTransactionRepository.save(knownMerchantMatcher.matchMerchantForTransaction(transactionEntity));
        return financialTransactionMapper.fromEntity(entityUpdated);
    }

    List<FinancialTransactionDto> findTransactionsWitchDateBetween(String from, String to) {
        LocalDate fromDate = LocalDate.parse(from, formatter);
        LocalDate toDate = LocalDate.parse(to, formatter);
        return financialTransactionRepository
                .findByTranDateBetweenOrderByTranDateAsc(fromDate, toDate)
                .stream()
                .map(financialTransactionMapper::fromEntity)
                .toList();
    }


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
