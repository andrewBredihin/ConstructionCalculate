package com.project.calculate.repository;

import com.project.calculate.entity.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceListRepository extends JpaRepository<PriceList, Long> {
}
