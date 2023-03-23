package com.project.calculate.repository;

import com.project.calculate.entity.MaterialCharacteristic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MaterialCharacteristicsRepository extends JpaRepository<MaterialCharacteristic, Long> {
    @Query(value = "Select * from Material_characteristics mc where mc.wedth = ?1 and mc.name = ?2", nativeQuery = true)
    MaterialCharacteristic getMaterialCharacteristicByWedthAndName(double wedth, String name);
}
