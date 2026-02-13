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

    static public PossibleMerchantDto fromEntity(PossibleMerchantEntity entity) {
        if (entity == null) {
            return null;
        }
        return new PossibleMerchantDto(
                entity.getId(),
                KnownMerchantDto.fromEntity(entity.getKnownMerchant()),
                entity.getPoints(),
                entity.getMatchedKeywords()
        );
    }
}
