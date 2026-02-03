package org.example.moneyflowspring.known_merchants;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "known_merchant_keywords",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"merchant_code", "keyword"})
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class KnownMerchantKeyWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "merchant_code",
            referencedColumnName = "merchantCode"
    )
    private KnownMerchantEntity merchant;
    private String keyword;
    private int weight;

    @Override
    public String toString() {
        return "KnownMerchantKeyWord{" +
                "id=" + id +
                ", merchant=" + merchant.getMerchantId() +
                ", merchant=" + merchant.getMerchantCode() +
                ", keyword='" + keyword + '\'' +
                ", weight=" + weight +
                '}';
    }
}
