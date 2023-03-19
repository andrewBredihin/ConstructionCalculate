package com.project.calculate.repository;

import com.project.calculate.entity.MeasurementUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementUnitRepository extends JpaRepository<MeasurementUnit, Long> {
}
