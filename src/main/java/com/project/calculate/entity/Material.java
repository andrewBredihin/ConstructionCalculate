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

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"mаterial_caracteristics_id\"", nullable = false)
    private MaterialCharacteristic mаterialCaracteristics;

    @Column(name = "material_type", nullable = false)
    private String materialType;

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

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public MaterialCharacteristic getMаterialCaracteristics() {
        return mаterialCaracteristics;
    }

    public void setMаterialCaracteristics(MaterialCharacteristic mаterialCaracteristics) {
        this.mаterialCaracteristics = mаterialCaracteristics;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}