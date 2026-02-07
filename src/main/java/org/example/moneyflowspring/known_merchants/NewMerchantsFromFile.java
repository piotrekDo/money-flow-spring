package org.example.moneyflowspring.known_merchants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class NewMerchantsFromFile {
    private final List<KnownMerchantEntity> savedMerchants;
    private final List<KnownMerchantEntity> duplicatedMerchants;
}
