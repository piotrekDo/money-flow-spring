package org.example.moneyflowspring.known_merchants;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.moneyflowspring.category.SubcategoryEntity;
import org.example.moneyflowspring.category.SubcategoryRepository;
import org.example.moneyflowspring.financial_transaction.FinancialTransactionEntity;
import org.example.moneyflowspring.financial_transaction.FinancialTransactionMapper;
import org.example.moneyflowspring.financial_transaction.FinancialTransactionRepository;
import org.example.moneyflowspring.financial_transaction.KnownMerchantMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class KnownMerchantsService {

    private final SubcategoryRepository subcategoryRepository;
    private final KnownMerchantsRepository knownMerchantsRepository;
    private final FinancialTransactionRepository financialTransactionRepository;
    private final KnownMerchantMapper knownMerchantMapper;
    private final KnownMerchantsFileReader knownMerchantsFileReader;
    private final KnownMerchantMatcher knownMerchantMatcher;


    public NewMerchantsFromFile retrieveKnownMerchantsFiles() {
        return knownMerchantsFileReader.retrieveKnownMerchantsFiles();
    }

    @Transactional
    KnownMerchantDto createNewKnownMerchant(AddNewKnownMerchantDto dto) {
        System.out.println(dto);

        Optional<KnownMerchantEntity> byMerchantCode = knownMerchantsRepository.findByMerchantCode(dto.getMerchantCode());
        List<KnownMerchantKeywordDto> keywordsProvided = dto.getKeywords();
        List<SubcategoryEntity> subcategories = new ArrayList<>();
        FinancialTransactionEntity transaction = null;

        if (byMerchantCode.isPresent()) {
            throw new IllegalStateException("Merchant with code " + dto.getMerchantCode() + " already exists");
        }
        if (keywordsProvided == null || keywordsProvided.isEmpty()) {
            throw new IllegalStateException("No keywords provided");
        }

        List<KnownMerchantKeyWordEntity> keywordsEntities = keywordsProvided.stream().map(k -> new KnownMerchantKeyWordEntity(
                null, null, k.getKeyword(), k.getWeight()
        )).toList();


        if (dto.getSubcategories() != null && !dto.getSubcategories().isEmpty()) {
            subcategories = subcategoryRepository.findAllById(dto.getSubcategories());
        }

        if (dto.getTranId() != null) {
            transaction = financialTransactionRepository.findById(dto.getTranId())
                    .orElseThrow(() -> new NoSuchElementException("Transaction id " + dto.getTranId() + " not found"));
        }

        if (transaction != null && subcategories.size() == 1) {
            transaction.setSubcategoryEntity(subcategories.getFirst());
            financialTransactionRepository.save(transaction);
        }

        KnownMerchantEntity knownMerchantEntityToSave = new KnownMerchantEntity(
                null,
                dto.getMerchantCode(),
                dto.getMerchantName(),
                dto.getImageUrl(),
                new ArrayList<>(),
                transaction != null ? List.of(transaction) : new ArrayList<>(),
                new ArrayList<>()
        );
        subcategories.forEach(knownMerchantEntityToSave::addSubcategory);
        keywordsEntities.forEach(knownMerchantEntityToSave::addKeyword);

        KnownMerchantEntity merchantEntitySaved = knownMerchantsRepository.save(knownMerchantEntityToSave);

        if (transaction != null) {
            transaction.setKnownMerchantEntity(merchantEntitySaved);
            transaction.setKnownMerchantUnsure(false);
            if (subcategories.size() == 1) {
                transaction.setSubcategoryEntity(subcategories.getFirst());
            }
            financialTransactionRepository.save(transaction);
        }


        List<FinancialTransactionEntity> updatedTransactionsToSave = financialTransactionRepository.findByKnownMerchantEntityIsNull()
                .stream()
                .peek(knownMerchantMatcher::matchMerchantForTransaction)
                .toList();
        financialTransactionRepository.saveAll(updatedTransactionsToSave);

        return knownMerchantMapper.merchantFromEntity(merchantEntitySaved);
    }

    KnownMerchantDto addSubcategoryToMerchant(long merchantId, long subcategoryId) {
        KnownMerchantEntity knownMerchantEntity = knownMerchantsRepository.findById(merchantId).orElseThrow(() -> new NoSuchElementException("Merchant with id: " + merchantId + " not found"));
        SubcategoryEntity subcategoryEntity = subcategoryRepository.findById(subcategoryId).orElseThrow(() -> new NoSuchElementException("Subcategory with id: " + subcategoryId + " not found"));
        knownMerchantEntity.addSubcategory(subcategoryEntity);
        KnownMerchantEntity merchantUpdated = knownMerchantsRepository.save(knownMerchantEntity);
        subcategoryRepository.save(subcategoryEntity);
        return knownMerchantMapper.merchantFromEntity(merchantUpdated);
    }

    List<KnownMerchantDto> findAllKnownMerchants() {
        return knownMerchantsRepository
                .findAll()
                .stream()
                .map(knownMerchantMapper::merchantFromEntity)
                .toList();
    }
}
