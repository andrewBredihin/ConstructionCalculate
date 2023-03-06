package com.project.calculate.repository;

import com.project.calculate.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ResultRepository extends JpaRepository<Result, Long> {

    @Query(value = "Select max(r.id) from results r", nativeQuery = true)
    Long getMaxId();
}
