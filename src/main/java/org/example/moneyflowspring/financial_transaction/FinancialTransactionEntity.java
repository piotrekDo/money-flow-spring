package org.example.moneyflowspring.financial_transaction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.moneyflowspring.known_merchants.KnownMerchantEntity;
import org.example.moneyflowspring.known_merchants.PossibleMerchantEntity;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long systemId;
    private TranType tranType;
    private LocalDate tranDate;
    private Double amount;
    @Column(unique = true)
    private String bankTranNr;
    private String accountNr;
    private String merchantDataRaw;
    private String titleRaw;
    private String normalizedKeywords;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<PossibleMerchantEntity> possibleMerchants;
    @ManyToOne
    private KnownMerchantEntity knownMerchantEntity;
    private boolean knownMerchantUnsure;
}
