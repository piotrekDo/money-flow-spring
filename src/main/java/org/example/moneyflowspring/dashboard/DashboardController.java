package org.example.moneyflowspring.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/transactions-month")
    List<DashboardTransactions> getTransactionsByMonth(@RequestParam String date) {
        return dashboardService.getDashboardTransactionsForMonth(date);
    }

    @GetMapping("/category-month")
    List<CategorySpendingDto> getCategorySpendingForMonth(@RequestParam String date) {
        return dashboardService.getCategorySpendingForMonth(date);
    }
}
