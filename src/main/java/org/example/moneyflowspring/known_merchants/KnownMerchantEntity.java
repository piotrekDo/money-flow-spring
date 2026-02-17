package org.example.moneyflowspring.known_merchants;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.moneyflowspring.category.SubcategoryEntity;
import org.example.moneyflowspring.financial_transaction.FinancialTransactionEntity;

import java.util.List;
import java.util.Objects;

@Entity(name = "known_merchants")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class KnownMerchantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long merchantId;
    @Column(unique = true)
    private String merchantCode;
    private String merchantName;
    private String imageUrl;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "merchant", fetch = FetchType.EAGER)
    private List<KnownMerchantKeyWordEntity> keywords;
    @OneToMany(mappedBy = "knownMerchantEntity", fetch = FetchType.LAZY)
    private List<FinancialTransactionEntity> financialTransactionsEntities;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "merchant_subcategories",
            joinColumns = @JoinColumn(name = "merchant_id"),
            inverseJoinColumns = @JoinColumn(name = "subcategory_id")
    )
    private List<SubcategoryEntity> subcategories;

    public void addKeyword(KnownMerchantKeyWordEntity keyword) {
        this.keywords.add(keyword);
        keyword.setMerchant(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        KnownMerchantEntity that = (KnownMerchantEntity) o;
        return Objects.equals(merchantId, that.merchantId) && Objects.equals(merchantCode, that.merchantCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(merchantId, merchantCode);
    }

    @Override
    public String toString() {
        return "KnownMerchantEntity{" +
                "merchantId=" + merchantId +
                ", merchantCode='" + merchantCode + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", keywords=" + keywords +
                '}';
    }

    void addSubcategory(SubcategoryEntity subcategory) {
        if (this.subcategories.contains(subcategory)) {
            throw new IllegalStateException("Subcategory with id: " + subcategory.getId() + " " + subcategory.getName() + " already exists at merchant with id: " + merchantId + " " +  merchantCode);
        }
        this.subcategories.add(subcategory);
        subcategory.getMerchants().add(this);
    }
}
