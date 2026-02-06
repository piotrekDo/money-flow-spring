package org.example.moneyflowspring.known_merchants;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.moneyflowspring.financial_transaction.FinancialTransactionEntity;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "possible_merchants")
public class PossibleMerchantEntity implements Comparable<PossibleMerchantEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private FinancialTransactionEntity transaction;

    @ManyToOne
    private KnownMerchantEntity knownMerchant;

    private int points;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "possible_merchant_keywords",
            joinColumns = @JoinColumn(name = "possible_merchant_id")

    )
    @Column(name = "keyword")
    private List<String> matchedKeywords;

    @Override
    public int compareTo(PossibleMerchantEntity o) {
        return Integer.compare(o.points, this.points);
    }

    @Override
    public String toString() {
        return "PossibleMerchantEntity{" +
                "id=" + id +
                ", merchant="  + knownMerchant.getMerchantCode() +
                ", points=" + points +
                ", matchedKeywords=" + matchedKeywords +
                '}';
    }
}

