package com.project.calculate.repository;

import com.project.calculate.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialsRepository extends JpaRepository<Material, Long> {

    Material findByName(String name);
}
