package org.example.moneyflowspring.dashboard;

import lombok.RequiredArgsConstructor;
import org.example.moneyflowspring.category.CategoryRepository;
import org.example.moneyflowspring.category.SubcategoryRepository;
import org.example.moneyflowspring.financial_transaction.FinancialTransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final FinancialTransactionRepository transactionRepository;

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
