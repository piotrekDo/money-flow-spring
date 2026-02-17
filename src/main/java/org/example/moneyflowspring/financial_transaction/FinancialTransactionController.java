package org.example.moneyflowspring.financial_transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class FinancialTransactionController {

    private final FinancialTransactionService service;

    @GetMapping("/date-between")
    FindFinancialTransactionsResponse findByTranDateBetweenOrderByTranDateAsc(
            @RequestParam String startDate,
            @RequestParam String endDate) {

        return service.findTransactionsWitchDateBetween(startDate, endDate);
    }

    @PostMapping("/recalculate-possible-merchants-by-id")
    FinancialTransactionDto recalculatePossibleMerchantsById(@RequestParam Long systemId) {
        return service.recalculateTransactionPossibleMerchantsBySystemId(systemId);
    }

    @PostMapping("/set-known-merchant")
    FinancialTransactionDto setKnownMerchant(
            @RequestParam Long tranSystemId,
            @RequestParam Long merchantId) {
        return service.setKnownMerchant(tranSystemId, merchantId);
    }

    @PostMapping("/add-subcategory")
    FinancialTransactionDto addSubcategory(@RequestParam Long tranSystemId,
                                           @RequestParam Long subcategoryId) {
        return service.addSubcategoryToTransaction(tranSystemId, subcategoryId);
    }
}
