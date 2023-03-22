package com.project.calculate.repository;

import com.project.calculate.entity.Calculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CalculationRepository extends JpaRepository<Calculation, Long> {

    @Query(value = "Select max(c.id) from calculation c", nativeQuery = true)
    Long getMaxId();

    @Query(value = "Select max(c.number) from calculation c where c.customer_id = ?1", nativeQuery = true)
    int getMaxNumber(Long customerId);
}
