package com.project.calculate.repository;

import com.project.calculate.entity.MaterialCharacteristic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialCharacteristicsRepository extends JpaRepository<MaterialCharacteristic, Long> {
}
