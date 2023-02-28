package com.project.calculate.entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "Results")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "material_characteristics_id", nullable = false)
    private MaterialCharacteristic materialCharacteristics;

    @Column(name = "material", nullable = false)
    private String material;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "measurement_unit", nullable = false)
    private String measurementUnit;

    @Column(name = "full_price", nullable = false)
    private Double fullPrice;

    @OneToMany(mappedBy = "results")
    private Set<StructuralElementBasement> structuralElementBasements = new LinkedHashSet<>();

    @OneToMany(mappedBy = "results")
    private Set<Calculation> calculations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "results")
    private Set<StructuralElementFrame> structuralElementFrames = new LinkedHashSet<>();

    public Set<StructuralElementFrame> getStructuralElementFrames() {
        return structuralElementFrames;
    }

    public void setStructuralElementFrames(Set<StructuralElementFrame> structuralElementFrames) {
        this.structuralElementFrames = structuralElementFrames;
    }

    public Set<Calculation> getCalculations() {
        return calculations;
    }

    public void setCalculations(Set<Calculation> calculations) {
        this.calculations = calculations;
    }

    public Set<StructuralElementBasement> getStructuralElementBasements() {
        return structuralElementBasements;
    }

    public void setStructuralElementBasements(Set<StructuralElementBasement> structuralElementBasements) {
        this.structuralElementBasements = structuralElementBasements;
    }

    public Double getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(Double fullPrice) {
        this.fullPrice = fullPrice;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public MaterialCharacteristic getMaterialCharacteristics() {
        return materialCharacteristics;
    }

    public void setMaterialCharacteristics(MaterialCharacteristic materialCharacteristics) {
        this.materialCharacteristics = materialCharacteristics;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}