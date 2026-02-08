package org.example.moneyflowspring.known_merchants;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class KnownMerchantDto {

    private Long merchantId;
    private String merchantCode;
    private String merchantName;
    private String imageUrl;
    List<KnownMerchantKeywordDto> keywords;
}
