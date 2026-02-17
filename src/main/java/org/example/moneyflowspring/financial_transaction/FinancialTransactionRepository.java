package org.example.moneyflowspring.financial_transaction;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FinancialTransactionRepository extends JpaRepository<FinancialTransactionEntity, Long> {

    List<FinancialTransactionEntity> findByTranDateBetween(LocalDate startDate, LocalDate endDate);

    List<FinancialTransactionEntity> findByTranDateBetweenOrderByTranDateAsc(LocalDate startDate, LocalDate endDate, Sort sort);

    List<FinancialTransactionEntity> findByPossibleMerchantsIsEmpty();

    List<FinancialTransactionEntity> findByPossibleMerchantsIsNotEmpty();

    List<FinancialTransactionEntity> findByKnownMerchantEntityIsNull();

    List<FinancialTransactionEntity> findByKnownMerchantEntityIsNotNull();

    List<FinancialTransactionEntity> findByNormalizedKeywordsContainingIgnoreCase(String keyword);

    @Query(value = """
                SELECT * 
                FROM financial_transaction_entity t
                WHERE EXISTS (
                    SELECT 1 
                    FROM unnest(:keywords) k 
                    WHERE LOWER(t.normalized_keywords) LIKE LOWER(CONCAT('%', k, '%'))
                )
                ORDER BY systemId DESC
            """, nativeQuery = true)
    List<FinancialTransactionEntity>
    findByNormalizedKeywordsContainingAny(@Param("keywords") List<String> keywords);


}
