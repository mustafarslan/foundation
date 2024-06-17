package com.foundation.core.repository;

import com.foundation.core.entity.Greet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GreetRepository extends JpaRepository<Greet, Long> {
    Greet findById(long id);
}
