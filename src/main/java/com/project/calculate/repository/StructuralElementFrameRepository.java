package com.project.calculate.repository;

import com.project.calculate.entity.StructuralElementFrame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StructuralElementFrameRepository extends JpaRepository<StructuralElementFrame, Long> {

    @Query(value = "Select max(s.id) from structural_element_frame s", nativeQuery = true)
    Long getMaxId();
}
