package org.example.moneyflowspring.financial_transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
public class FindFinancialTransactionsResponse {
    private LocalDate startDate;
    private LocalDate endDate;
    private List<FinancialTransactionDto> transactions;
    private int allTransactionCount;
    private int unknownMerchantTransactionCount;
    private int incomeTransactionCount;
    private int expenseTransactionCount;
    private int cashTransactionCount;
    private int missingDataTransactionCount;
    private double totals;
    private double totalExpense;
    private double totalIncome;
    private double totalCashIn;
    private double totalCashOut;
}
