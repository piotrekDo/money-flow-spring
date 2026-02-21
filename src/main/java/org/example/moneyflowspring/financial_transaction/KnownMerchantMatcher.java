package org.example.moneyflowspring.financial_transaction;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.moneyflowspring.category.SubcategoryEntity;
import org.example.moneyflowspring.category.SubcategoryRepository;
import org.example.moneyflowspring.known_merchants.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class KnownMerchantMatcher {
    private final static long ATM_MERCHANT_ID = 1659L;
    private final static long CASH_IN_ID = 49L;
    private final static long CASH_OUT_ID = 50L;

    private final KnownMerchantKeyWordRepository knownMerchantKeyWordRepository;
    private final KnownMerchantsRepository knownMerchantsRepository;
    private final SubcategoryRepository subcategoryRepository;
    private KnownMerchantEntity atmMerchant = null;
    private SubcategoryEntity cashInSubcategory = null;
    private SubcategoryEntity cashOutSubcategory = null;

    private void matchAtmMerchant(FinancialTransactionEntity transaction) {
        if (atmMerchant == null) {
            this.atmMerchant = knownMerchantsRepository.findById(ATM_MERCHANT_ID).orElseThrow(() -> new NoSuchElementException("atmMerchant not found"));
        }

        transaction.setKnownMerchantEntity(atmMerchant);
        transaction.setKnownMerchantUnsure(false);

        switch (transaction.getTranType()) {
            case CASH_OUT -> {
                if (cashOutSubcategory == null) {
                    this.cashOutSubcategory = subcategoryRepository.findById(CASH_OUT_ID).orElseThrow(() -> new NoSuchElementException("cashOutSubcategory not found"));
                }
                transaction.setSubcategoryEntity(cashOutSubcategory);
            }
            case CASH_IN -> {
                if (cashInSubcategory == null) {
                    this.cashInSubcategory = subcategoryRepository.findById(CASH_IN_ID).orElseThrow(() -> new NoSuchElementException("cashInSubcategory not found"));
                }
                transaction.setSubcategoryEntity(cashInSubcategory);
            }
            default -> throw new IllegalStateException("Unexpected value: " + transaction.getTranType());
        }

    }

    FinancialTransactionEntity matchMerchantForTransaction(FinancialTransactionEntity transaction) {
        TranType tranType = transaction.getTranType();
        if (tranType == TranType.CASH_IN || tranType == TranType.CASH_OUT) {
            matchAtmMerchant(transaction);
        } else {
            Set<String> transactionKeywords = new HashSet<>(Arrays.asList(transaction.getNormalizedKeywords().split(" ")));

            List<KnownMerchantKeyWordEntity> keyWordsFound = knownMerchantKeyWordRepository.findByKeywordIn(transactionKeywords);
            HashMap<Long, PossibleMerchantEntity> possibleMerchants = new HashMap<>();

            for (KnownMerchantKeyWordEntity keyWord : keyWordsFound) {
                Long merchantId = keyWord.getMerchant().getMerchantId();
                String keyword = keyWord.getKeyword();
                int weight = keyWord.getWeight();

                PossibleMerchantEntity possibleMerchant = possibleMerchants.get(merchantId);
                if (possibleMerchant == null) {
                    possibleMerchant = new PossibleMerchantEntity(null, null, keyWord.getMerchant(), 0, new ArrayList<>());
                    possibleMerchants.put(merchantId, possibleMerchant);
                }
                possibleMerchant.getMatchedKeywords().add(keyword);
                possibleMerchant.setPoints(possibleMerchant.getPoints() + weight);

                List<PossibleMerchantEntity> sortedPossibleMerchants = possibleMerchants.values().stream().filter(m -> m.getPoints() > 1).sorted().limit(3).toList();
                transaction.getPossibleMerchants().clear();
                transaction.getPossibleMerchants().addAll(sortedPossibleMerchants);
                sortedPossibleMerchants.forEach(possMer -> {
                    possMer.setTransaction(transaction);
                });

                List<PossibleMerchantEntity> strongMatches = sortedPossibleMerchants.stream().filter(pm -> pm.getPoints() >= 3).toList();
                if (strongMatches.size() == 1) {
                    PossibleMerchantEntity winner = strongMatches.getFirst();

                    KnownMerchantEntity winnerMerchant = winner.getKnownMerchant();
                    transaction.setKnownMerchantEntity(winnerMerchant);
                    transaction.setKnownMerchantUnsure(false);

                    if (winnerMerchant.getSubcategories().size() == 1) {
                        SubcategoryEntity onlySubcategory = winnerMerchant.getSubcategories().getFirst();
                        transaction.setSubcategoryEntity(onlySubcategory);
                    }
                }
            }
        }

        return transaction;
    }
}
