package org.example.moneyflowspring.dashboard;

import lombok.RequiredArgsConstructor;
import org.example.moneyflowspring.category.CategoryRepository;
import org.example.moneyflowspring.category.SubcategoryRepository;
import org.example.moneyflowspring.financial_transaction.FinancialTransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final FinancialTransactionRepository transactionRepository;

    List<DashboardTransactions> getDashboardTransactionsForMonth(String dateString) {
        LocalDate date = LocalDate.parse(dateString, formatter);
        LocalDate afterEquals = date.withDayOfMonth(1);
        LocalDate beforeMonth = afterEquals.plusMonths(1);

        List<TransactionWithCategory> transactions = transactionRepository
                .findTransactionsWithCategroiesByDate(afterEquals, beforeMonth);
        List<CategorySummary> categorySummaries = categoryRepository
                .getCategorySummaryByDate(afterEquals, beforeMonth);


        Map<Long, List<TransactionWithCategory>> transactionsByCategory = transactions.stream()
                .collect(Collectors.groupingBy(
                        tx -> tx.getCategoryId() != null ? tx.getCategoryId() : 0L
                ));

        if (transactionsByCategory.containsKey(0L)) {
            CategorySummary missingCategory = new CategorySummary();
            missingCategory.setCategoryId(0L);
            missingCategory.setCategoryName("Brak kategorii");
            missingCategory.setCategoryIcon(null);
            missingCategory.setCategoryColor("#CCCCCC");
            missingCategory.setCategoryIsPositive(false);
            categorySummaries.add(missingCategory);
        }

        return categorySummaries.stream()
                .map(summary -> new DashboardTransactions(
                        summary,
                        transactionsByCategory.getOrDefault(summary.getCategoryId(), Collections.emptyList())
                ))
                .toList();
    }

    public List<CategorySpendingDto> getCategorySpendingForMonth(String dateString) {
        LocalDate date = LocalDate.parse(dateString, formatter);

        LocalDate startOfMonth = date.withDayOfMonth(1);
        LocalDate endOfMonth = date.withDayOfMonth(date.lengthOfMonth());

        LocalDate threeMonthsAgoStart = startOfMonth.minusMonths(3);
        LocalDate lastMonthEnd = startOfMonth.minusDays(1);

        return transactionRepository.findCurrentMonthGroupedByCategory(
                threeMonthsAgoStart,
                lastMonthEnd,
                startOfMonth,
                endOfMonth
        );
    }
}
