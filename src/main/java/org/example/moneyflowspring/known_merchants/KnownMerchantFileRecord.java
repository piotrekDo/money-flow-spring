package org.example.moneyflowspring.known_merchants;

import lombok.Getter;

@Getter
public class KnownMerchantFileRecord {
    private final String merchantCode;
    private final String merchantName;
    private final String keyword;
    private final int weight;

    public KnownMerchantFileRecord(String line) {
        String[] fields = line.split(",");
        this.merchantCode = fields[0].trim();
        this.merchantName = fields[1].trim();
        this.keyword = fields[2].trim();
        this.weight = Integer.parseInt(fields[3].trim());
    }

    @Override
    public String toString() {
        return "KnownMerchantFileRecord{" +
                "id='" + merchantCode + '\'' +
                ", name='" + merchantName + '\'' +
                ", keyword='" + keyword + '\'' +
                ", weight=" + weight +
                '}';
    }
}
