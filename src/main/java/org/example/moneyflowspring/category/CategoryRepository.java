package org.example.moneyflowspring.category;

import org.example.moneyflowspring.dashboard.CategorySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Query(value = """
            SELECT
                c.id             AS categoryId,
                c.name           AS categoryName,
                c.icon           AS categoryIcon,
                c.color          AS categoryColor,
                COALESCE(c.is_positive, false) AS categoryIsPositive,
                COALESCE(SUM(t.amount), 0) AS totalAmount
            FROM category_entity c
            LEFT JOIN subcategory_entity s\s
                ON s.category_id = c.id
            LEFT JOIN financial_transaction_entity t\s
                ON t.subcategory_entity_id = s.id
                AND t.tran_date >= :afterEquals
                AND t.tran_date < :before
            GROUP BY c.id, c.name, c.color, c.icon, c.is_positive
            ORDER BY c.is_positive DESC NULLS LAST, c.id ASC NULLS LAST;
            """, nativeQuery = true)
    List<CategorySummary> getCategorySummaryByDate(
            @Param("afterEquals") LocalDate afterEquals,
            @Param("before") LocalDate before
    );
}
