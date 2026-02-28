package org.example.moneyflowspring.financial_transaction;

import org.example.moneyflowspring.dashboard.CategorySpendingDto;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FinancialTransactionRepository extends JpaRepository<FinancialTransactionEntity, Long> {

    @Query(value = """
            WITH last_three_months AS (
                SELECT 
                    cat.id AS category_id,
                    SUM(tran.amount) AS total_amount
                FROM category_entity cat
                LEFT JOIN subcategory_entity sub ON sub.category_id = cat.id
                LEFT JOIN financial_transaction_entity tran 
                       ON tran.subcategory_entity_id = sub.id
                      AND tran.tran_date BETWEEN :prevStart AND :prevEnd
                GROUP BY cat.id
            )
            SELECT 
                cat.id AS id,
                cat.color AS color,
                cat.image_url AS imageUrl,
                cat.name AS name,
                cat.icon AS icon,
                cat.is_positive AS isPositive,
                SUM(tran.amount) as totalCurrentMonth,
                COUNT(tran.system_id) as transactionsCountCurrentMonth,
                COALESCE(l3.total_amount / 3.0, 0) AS averageLastThreeMonths,
                CASE 
                    WHEN COALESCE(l3.total_amount / 3.0, 0) = 0 THEN NULL
                    ELSE (SUM(tran.amount) - (l3.total_amount / 3.0)) / (l3.total_amount / 3.0) * 100
                END AS trendPercent
            FROM category_entity cat
            LEFT JOIN subcategory_entity sub ON sub.category_id = cat.id
            LEFT JOIN financial_transaction_entity tran 
                   ON tran.subcategory_entity_id = sub.id
                  AND tran.tran_date BETWEEN :currStart AND :currEnd
            LEFT JOIN last_three_months l3 ON l3.category_id = cat.id
            GROUP BY cat.id, cat.color, cat.image_url, cat.name, cat.icon, cat.is_positive, l3.total_amount
            ORDER BY totalCurrentMonth ASC
            """, nativeQuery = true)
    List<CategorySpendingDto> findCurrentMonthGroupedByCategory(
            @Param("prevStart") LocalDate prevStart,
            @Param("prevEnd") LocalDate prevEnd,
            @Param("currStart") LocalDate currStart,
            @Param("currEnd") LocalDate currEnd
    );

    List<FinancialTransactionEntity> findByTranDateBetween(LocalDate startDate, LocalDate endDate);

    List<FinancialTransactionEntity> findByTranDateBetweenOrderByTranDateAsc(LocalDate startDate, LocalDate endDate, Sort sort);

    List<FinancialTransactionEntity> findAllByKnownMerchantEntity_MerchantIdAndTranDateBetweenOrderByTranDateAsc(Long merchantId, LocalDate startDate, LocalDate endDate);

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
