package org.example.moneyflowspring.financial_transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class FinancialTransactionController {

    private final FinancialTransactionService service;

    @GetMapping("/date-between")
    public List<FinancialTransactionDto> findByTranDateBetweenOrderByTranDateAsc(
            @RequestParam String startDate,
            @RequestParam String endDate) {

        return service.findTransactionsWitchDateBetween(startDate, endDate);
    }
}
