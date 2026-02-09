package org.example.moneyflowspring.known_merchants;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class PossibleMerchantDto {
    private Long id;
    private KnownMerchantDto knownMerchantDto;
    private int points;
    private List<String> matchedKeywords;
}
