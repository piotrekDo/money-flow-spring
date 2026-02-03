package org.example.moneyflowspring.known_merchants;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KnownMerchantsRepository extends JpaRepository<KnownMerchantEntity, Long> {
}
