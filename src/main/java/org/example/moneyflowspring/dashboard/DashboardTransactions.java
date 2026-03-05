package org.example.moneyflowspring.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class DashboardTransactions {
    private  CategorySummary categorySummary;
    private  List<TransactionWithCategory> transactions;
}
