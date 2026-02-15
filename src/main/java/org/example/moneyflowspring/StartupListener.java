package org.example.moneyflowspring;

import lombok.AllArgsConstructor;
import org.example.moneyflowspring.category.CategoryDto;
import org.example.moneyflowspring.category.CategoryService;
import org.example.moneyflowspring.category.SubcategoryDto;
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

//        categoryService.createCategory(new CategoryDto(null, "Dochód", null, "cash-plus", "#4CAF50", null));
//        categoryService.createCategory(new CategoryDto(null, "Pozostałe", null, "dots-horizontal-circle-outline", "#9E9E9E", null));

//        categoryService.createCategory(new CategoryDto(null, "Rachunki i opłaty stałe", null,  2, "#FF9800", null));
//        categoryService.createCategory(new CategoryDto(null, "Żywność i przekąski", null,  3, "#FFC107", null));
//        categoryService.createCategory(new CategoryDto(null, "Jedzenie na wynos / dostawa", null,  4, "#FF5722", null));
//        categoryService.createCategory(new CategoryDto(null, "Rozrywka", null,  5, "#9C27B0", null));
//        categoryService.createCategory(new CategoryDto(null, "Alkohol", null,  6, "#795548", null));
//        categoryService.createCategory(new CategoryDto(null, "Dom i mieszkanie", null,  7, "#3F51B5", null));
//        categoryService.createCategory(new CategoryDto(null, "Transport", null,  8, "#2196F3", null));
//        categoryService.createCategory(new CategoryDto(null, "Zdrowie i higiena", null,  9, "#E91E63", null));
//        categoryService.createCategory(new CategoryDto(null, "Ubrania i obuwie", null,  10, "#00BCD4", null));
//        categoryService.createCategory(new CategoryDto(null, "Edukacja / rozwój", null,  11, "#009688", null));

//        categoryService.createSubcategory(new SubcategoryDto(null, "Pozostałe Dochody", null, "gift-outline", "#9E9E9E", null, null, null, null, null));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Inne zakupy", null, "basket-outline", "#607D8B", null, null, null, null, null));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Niespodziewane wydatki", null, "alert-circle-outline", "#FF9F1C", null, null, null, null, null));

//        categoryService.createSubcategory(new SubcategoryDto(null, "Wynagrodzenie", null,  13, "#4CAF50"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Czynsz", null,  15, "#F4A261"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Prąd", null,  16, "#9B5DE5"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Gaz", null,  17, "#FFC107"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Woda", null,  18, "#2196F3"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Telefon", null,  19, "#607D8B"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Internet", null,  20, "#2EC4B6"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Kredyt", null,  21, "#795548"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Żywność", null,  22, "#4CAF50"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Przekąski", null,  23, "#FF5722"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Glovo", null,  24, "#FFC107"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Pyszne", null,  25, "#FF9F1C"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Restauracja", null,  26, "#00F5D4"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Kino", null,  27, "#9C27B0"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Gry", null,  5, "#7EA0FF"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Gry planszowe", null,  28, "#00FA9A"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Koncerty", null,  29, "#607D8B"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Subskrypcje", null,  30, "#E71D36"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Sklep", null,  3, "#E71D36"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Na mieście", null,  31, "#FF9F1C"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Środki czystości", null,  32, "#BDE0FE"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Meble", null,  33, "#2EC4B6"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Dekoracje", null,  34, "#7FFF00"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Sprzęty domowe", null,  35, "#00BCD4"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Paliwo moto", null,  24, "#2196F3"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Paliwo auto", null,  36, "#FF5722"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Naprawa moto", null,  37, "#E71D36"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Naprawa auto", null,  38, "#F4A261"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Ubezpieczenie moto", null,  39, "#795548"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Ubezpieczenie auto", null,  40, "#607D8B"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Taxi", null,  41, "#FFC107"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Bilety", null,  42, "#F4A261"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Leki", null,  43, "#FF1493"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Lekarz", null,  44, "#E71D36"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Kosmetyki", null,  45, "#9C27B0"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Fryzjer", null,  46, "#00FA9A"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Siłownia", null,  47, "#00BCD4"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Ubrania", null,  10, "#00FA9A"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Obuwie", null,  48, "#7FFF0"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Akcesoria", null,  49, "#FF1493"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Książki", null,  50, "#795548"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Kurs online", null,  51, "#FF1493"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Szkolenie", null,  52, "#7EA0FF"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Materiały edukacyjne", null,  53, "#2EC4B6"));
//        categoryService.createSubcategory(new SubcategoryDto(null, "Subskrypcje", null,  30, "#E71D36"));
    }
}
