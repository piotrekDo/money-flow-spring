package org.example.moneyflowspring.financial_transaction;

import lombok.RequiredArgsConstructor;
import org.example.moneyflowspring.known_merchants.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class KnownMerchantMatcher {
    private final KnownMerchantKeyWordRepository knownMerchantKeyWordRepository;

    FinancialTransactionEntity matchMerchantForTransaction(FinancialTransactionEntity transaction) {
        Set<String> transactionKeywords =
                new HashSet<>(Arrays.asList(transaction.getNormalizedKeywords().split(" ")));

        List<KnownMerchantKeyWordEntity> keyWordsFound = knownMerchantKeyWordRepository.findByKeywordIn(transactionKeywords);
        HashMap<Long, PossibleMerchantEntity> possibleMerchants = new HashMap<>();

        for (KnownMerchantKeyWordEntity keyWord : keyWordsFound) {
            Long merchantId = keyWord.getMerchant().getMerchantId();
            String keyword = keyWord.getKeyword();
            int weight = keyWord.getWeight();

            PossibleMerchantEntity possibleMerchant = possibleMerchants.get(merchantId);
            if (possibleMerchant == null) {
                possibleMerchant = new PossibleMerchantEntity(
                        null, null, keyWord.getMerchant(), 0, new ArrayList<>()
                );
                possibleMerchants.put(merchantId, possibleMerchant);
            }
            possibleMerchant.getMatchedKeywords().add(keyword);
            possibleMerchant.setPoints(possibleMerchant.getPoints() + weight);

            List<PossibleMerchantEntity> sortedPossibleMerchants = possibleMerchants.values()
                    .stream()
                    .filter(m -> m.getPoints() > 1).
                    sorted()
                    .limit(3)
                    .toList();
            transaction.getPossibleMerchants().clear();
            transaction.getPossibleMerchants().addAll(sortedPossibleMerchants);
            sortedPossibleMerchants.forEach(possMer -> {
                possMer.setTransaction(transaction);
            });

            List<PossibleMerchantEntity> strongMatches = sortedPossibleMerchants.stream()
                    .filter(pm -> pm.getPoints() >= 3)
                    .toList();
            if (strongMatches.size() == 1) {
                PossibleMerchantEntity winner = strongMatches.getFirst();

                transaction.setKnownMerchantEntity(winner.getKnownMerchant());
                transaction.setKnownMerchantUnsure(false);
            }
        }
        return transaction;
    }
}
