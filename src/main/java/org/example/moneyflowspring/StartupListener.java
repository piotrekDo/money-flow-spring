package org.example.moneyflowspring;

import lombok.AllArgsConstructor;
import org.example.moneyflowspring.category.CategoryService;
import org.example.moneyflowspring.financial_transaction.FinancialTransactionService;
import org.example.moneyflowspring.financial_transaction.NewTransactionsFromIngFile;
import org.example.moneyflowspring.known_merchants.KnownMerchantsService;
import org.example.moneyflowspring.known_merchants.NewMerchantsFromFile;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StartupListener {

    private final KnownMerchantsService knownMerchantsService;
    private final FinancialTransactionService financialTransactionService;
    private final CategoryService categoryService;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        NewMerchantsFromFile newMerchantsFromFile = knownMerchantsService.retrieveKnownMerchantsFiles();
        NewTransactionsFromIngFile newTransactionsFromIngFile = financialTransactionService.retrieveNewTransactionsFormIngFiles();

        System.out.println("Wporowadzono " +  newMerchantsFromFile.getSavedMerchants().size() + " nowych merchantów");
        System.out.println("Wykryto " +  newMerchantsFromFile.getDuplicatedMerchants().size() + " zduplikowanych merchantów");

        System.out.println("Wporowadzono " +  newTransactionsFromIngFile.getSavedTransactions().size() + " nowych transakcji");
        System.out.println("Wykryto " +  newTransactionsFromIngFile.getDuplicatedTransactions().size() + " zduplikowamych transakcji");


    }
}
