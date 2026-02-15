package org.example.moneyflowspring.financial_transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class FinancialTransactionController {

    private final FinancialTransactionService service;

    @GetMapping("/date-between")
    List<FinancialTransactionDto> findByTranDateBetweenOrderByTranDateAsc(
            @RequestParam String startDate,
            @RequestParam String endDate) {

        return service.findTransactionsWitchDateBetween(startDate, endDate);
    }

    @PostMapping("/recalculate-possible-merchants-by-id")
    FinancialTransactionDto recalculatePossibleMerchantsById(@RequestParam Long systemId) {
        return service.recalculateTransactionPossibleMerchantsBySystemId(systemId);
    }
}
