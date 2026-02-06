package org.example.moneyflowspring;

import lombok.AllArgsConstructor;
import org.example.moneyflowspring.financial_transaction.FinancialTransactionService;
import org.example.moneyflowspring.financial_transaction.NewTransactionsFromIngFile;
import org.example.moneyflowspring.known_merchants.KnownMerchantsService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StartupListener {

    private final KnownMerchantsService knownMerchantsService;
    private final FinancialTransactionService financialTransactionService;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        knownMerchantsService.retrieveKnownMerchantsFiles();
        NewTransactionsFromIngFile newTransactionsFromIngFile = financialTransactionService.retrieveNewTransactionsFormIngFiles();

        System.out.println("--- SAVED TRANSACTIONS ---");
        newTransactionsFromIngFile.getSavedTransactions().forEach(System.out::println);

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("--- DUPLICATED TRANSACTIONS ---");
        newTransactionsFromIngFile.getDuplicatedTransactions().forEach(System.out::println);

    }
}
