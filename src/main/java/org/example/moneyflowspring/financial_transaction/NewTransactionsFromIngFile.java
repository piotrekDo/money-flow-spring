package org.example.moneyflowspring.financial_transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class NewTransactionsFromIngFile {
    private final List<FinancialTransactionEntity> savedTransactions;
    private final List<FinancialTransactionEntity> duplicatedTransactions;
}
