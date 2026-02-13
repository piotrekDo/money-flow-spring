package org.example.moneyflowspring.category;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.moneyflowspring.financial_transaction.FinancialTransactionEntity;
import org.example.moneyflowspring.known_merchants.KnownMerchantEntity;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubcategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String imageUrl;
    private int iconId;
    private String color;
    @ManyToOne(fetch = FetchType.EAGER)
    private CategoryEntity category;
    @ManyToMany(mappedBy = "subcategories")
    private List<KnownMerchantEntity> merchants;
    @OneToMany(mappedBy = "subcategoryEntity")
    private List<FinancialTransactionEntity> financialTransactions;
}
