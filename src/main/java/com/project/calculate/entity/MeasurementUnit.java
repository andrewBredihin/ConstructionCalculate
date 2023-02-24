package com.project.calculate.entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "measurement_units")
public class MeasurementUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "measurement_units_name", nullable = false, length = 20)
    private String measurementUnitsName;

    @OneToMany(mappedBy = "measurementUnit")
    private Set<MaterialCharacteristic> materialCharacteristics = new LinkedHashSet<>();

    public Set<MaterialCharacteristic> getMaterialCharacteristics() {
        return materialCharacteristics;
    }

    public void setMaterialCharacteristics(Set<MaterialCharacteristic> materialCharacteristics) {
        this.materialCharacteristics = materialCharacteristics;
    }

    public String getMeasurementUnitsName() {
        return measurementUnitsName;
    }

    public void setMeasurementUnitsName(String measurementUnitsName) {
        this.measurementUnitsName = measurementUnitsName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}