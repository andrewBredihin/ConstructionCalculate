package com.project.calculate.repository;

import com.project.calculate.entity.CalculationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculationStatusRepository extends JpaRepository<CalculationStatus, Long> {
    CalculationStatus findByTitle(String title);
}
