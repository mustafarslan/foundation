package com.foundation.core.repository;

import com.foundation.core.entity.Money;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MoneyRepository extends JpaRepository<Money, Long> {
    Optional<Money> findByCustomerId(Long customerId);
}
