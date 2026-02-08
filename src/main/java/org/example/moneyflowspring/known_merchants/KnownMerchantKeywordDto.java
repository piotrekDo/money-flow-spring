package org.example.moneyflowspring.known_merchants;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class KnownMerchantKeywordDto {
    private Long id;
    private String keyword;
    private int weight;
}
