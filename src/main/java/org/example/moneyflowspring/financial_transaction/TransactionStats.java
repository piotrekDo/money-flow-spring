package org.example.moneyflowspring.financial_transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
class TransactionStats {
    long unknownMerchantCount = 0;
    long incomeCount = 0;
    long expenseCount = 0;
    long cashCount = 0;
    long missingDataCount = 0;

    double totals = 0;
    double totalIncome = 0;
    double totalExpense = 0;
    double totalCashIn = 0;
    double totalCashOut = 0;
}