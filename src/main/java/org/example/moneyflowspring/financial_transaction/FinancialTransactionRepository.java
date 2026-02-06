package org.example.moneyflowspring.financial_transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialTransactionRepository extends JpaRepository<FinancialTransactionEntity, Long> {
}
