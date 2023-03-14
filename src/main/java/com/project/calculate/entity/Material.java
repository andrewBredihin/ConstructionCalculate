package com.project.calculate.entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "Materials")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "material_type", nullable = false)
    private String materialType;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "structural_element_type", nullable = false)
    private String structuralElementType;

    @OneToMany(mappedBy = "materials")
    private Set<MaterialCharacteristic> materialCharacteristics = new LinkedHashSet<>();

    public Set<MaterialCharacteristic> getMaterialCharacteristics() {
        return materialCharacteristics;
    }

    public void setMaterialCharacteristics(Set<MaterialCharacteristic> materialCharacteristics) {
        this.materialCharacteristics = materialCharacteristics;
    }

    public String getStructuralElementType() {
        return structuralElementType;
    }

    public void setStructuralElementType(String structuralElementType) {
        this.structuralElementType = structuralElementType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }
}