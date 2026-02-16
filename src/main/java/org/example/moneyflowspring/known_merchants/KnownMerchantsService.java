package org.example.moneyflowspring.known_merchants;


import lombok.AllArgsConstructor;
import org.example.moneyflowspring.category.SubcategoryEntity;
import org.example.moneyflowspring.category.SubcategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class KnownMerchantsService {

    private final KnownMerchantsRepository knownMerchantsRepository;
    private final KnownMerchantMapper knownMerchantMapper;
    private final KnownMerchantsFileReader knownMerchantsFileReader;
    private final SubcategoryRepository subcategoryRepository;

    public NewMerchantsFromFile retrieveKnownMerchantsFiles() {
        return knownMerchantsFileReader.retrieveKnownMerchantsFiles();
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
