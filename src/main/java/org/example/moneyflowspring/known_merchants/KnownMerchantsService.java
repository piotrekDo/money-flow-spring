package org.example.moneyflowspring.known_merchants;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class KnownMerchantsService {

    private final KnownMerchantsRepository knownMerchantsRepository;
    private final KnownMerchantMapper knownMerchantMapper;
    private final KnownMerchantsFileReader knownMerchantsFileReader;


    public NewMerchantsFromFile retrieveKnownMerchantsFiles() {
        return knownMerchantsFileReader.retrieveKnownMerchantsFiles();
    }

    List<KnownMerchantDto> findAllKnownMerchants() {
        return knownMerchantsRepository
                .findAll()
                .stream()
                .map(knownMerchantMapper::merchantFromEntity)
                .toList();
    }
}
