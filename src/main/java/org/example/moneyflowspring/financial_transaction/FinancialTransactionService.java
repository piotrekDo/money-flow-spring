package org.example.moneyflowspring.financial_transaction;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.moneyflowspring.category.SubcategoryEntity;
import org.example.moneyflowspring.category.SubcategoryRepository;
import org.example.moneyflowspring.known_merchants.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
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
    private final SubcategoryRepository subcategoryRepository;

    @Transactional
    FinancialTransactionDto setKnownMerchant(long tranSystemId, long merchantId) {
        FinancialTransactionEntity transactionEntity = financialTransactionRepository.findById(tranSystemId).orElseThrow(() -> new NoSuchElementException("Transaction with id " + tranSystemId + " not found"));
        KnownMerchantEntity knownMerchantEntity = knownMerchantsRepository.findById(merchantId).orElseThrow(() -> new NoSuchElementException("Merchant not found with id: " + merchantId));

        transactionEntity.setKnownMerchantEntity(knownMerchantEntity);
        transactionEntity.setKnownMerchantUnsure(false);
        if (knownMerchantEntity.getSubcategories().size() == 1) {
            SubcategoryEntity onlySubcategory = knownMerchantEntity.getSubcategories().get(0);
            transactionEntity.setSubcategoryEntity(onlySubcategory);
        }

        FinancialTransactionEntity updatedTransactionEntity = financialTransactionRepository.save(transactionEntity);
        return financialTransactionMapper.fromEntity(updatedTransactionEntity);
    }

    FinancialTransactionDto recalculateTransactionPossibleMerchantsBySystemId(Long systemId) {
        FinancialTransactionEntity transactionEntity = financialTransactionRepository.findById(systemId).orElseThrow();
        FinancialTransactionEntity entityUpdated = financialTransactionRepository.save(knownMerchantMatcher.matchMerchantForTransaction(transactionEntity));
        return financialTransactionMapper.fromEntity(entityUpdated);
    }

    FindFinancialTransactionsResponse findTransactionsWitchDateBetween(String from, String to) {
        LocalDate fromDate = LocalDate.parse(from, formatter);
        LocalDate toDate = LocalDate.parse(to, formatter);

        List<FinancialTransactionDto> transactionsFound = financialTransactionRepository
                .findByTranDateBetweenOrderByTranDateAsc(fromDate, toDate, Sort.by(Sort.Direction.DESC, "systemId"))
                .stream()
                .map(financialTransactionMapper::fromEntity)
                .toList();

        TransactionStats stats = new TransactionStats();

        for (FinancialTransactionDto t : transactionsFound) {
            stats.totals += t.getAmount();

            if (t.getKnownMerchant() == null) stats.unknownMerchantCount++;
            if (t.getSubcategoryDto() == null || t.getKnownMerchant() == null) stats.missingDataCount++;

            TranType type = t.getTranType();
            boolean isCash = type == TranType.CASH_IN || type == TranType.CASH_OUT;
            if (isCash) stats.cashCount++;

            if (!isCash) {
                if (type == TranType.INCOME) stats.incomeCount++;
                if (type == TranType.EXPENSE) stats.expenseCount++;
                if (t.getAmount() > 0) stats.totalIncome += t.getAmount();
                if (t.getAmount() < 0) stats.totalExpense += Math.abs(t.getAmount());
            } else {
                if (t.getAmount() > 0) stats.totalCashIn += t.getAmount();
                if (t.getAmount() < 0) stats.totalCashOut += Math.abs(t.getAmount());
            }
        }

        return new FindFinancialTransactionsResponse(
                fromDate,
                toDate,
                transactionsFound,
                transactionsFound.size(),
                (int) stats.unknownMerchantCount,
                (int) stats.incomeCount,
                (int) stats.expenseCount,
                (int) stats.cashCount,
                (int) stats.missingDataCount,
                stats.totals,
                stats.totalExpense,
                stats.totalIncome,
                stats.totalCashIn,
                stats.totalCashOut
        );
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

    FinancialTransactionDto addSubcategoryToTransaction(Long tranSystemId, Long subcategoryId) {
        FinancialTransactionEntity transactionEntity = financialTransactionRepository.findById(tranSystemId).orElseThrow(() -> new NoSuchElementException("Transaction with id " + tranSystemId + " does not exist"));
        SubcategoryEntity subcategoryEntity = subcategoryRepository.findById(subcategoryId).orElseThrow(() -> new NoSuchElementException("Subcategory with id " + subcategoryId + " does not exist"));

        transactionEntity.setSubcategoryEntity(subcategoryEntity);
        FinancialTransactionEntity transactionEntityUpdated = financialTransactionRepository.save(transactionEntity);
        return financialTransactionMapper.fromEntity(transactionEntityUpdated);
    }
}
