package org.example.moneyflowspring.known_merchants;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class AddNewKnownMerchantDto {
    private String merchantCode;
    private String merchantName;
    private String imageUrl;
    private List<KnownMerchantKeywordDto> keywords;
    private List<Long> subcategories;
    private Long tranId;
}
