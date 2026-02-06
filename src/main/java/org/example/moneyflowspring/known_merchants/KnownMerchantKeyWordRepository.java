package org.example.moneyflowspring.known_merchants;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface KnownMerchantKeyWordRepository extends JpaRepository<KnownMerchantKeyWordEntity, Long> {

    List<KnownMerchantKeyWordEntity> findByKeywordIn(
            Collection<String> keywords
    );
}
