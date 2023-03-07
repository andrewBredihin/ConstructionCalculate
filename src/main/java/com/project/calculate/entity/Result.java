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
    private Long id;


    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "full_price", nullable = false)
    private Double fullPrice;

    @Column(name = "material", nullable = false)
    private String material;

    @Column(name = "measurement_unit", nullable = false)
    private String measurementUnit;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "material_characteristics_id", nullable = false)
    private MaterialCharacteristic materialCharacteristics;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "calculation_id", nullable = false)
    private Calculation calculation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "structural_element_frame_id", nullable = false)
    private StructuralElementFrame structuralElementFrame;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "structural_element_basement_id", nullable = false)
    private StructuralElementBasement structuralElementBasement;

    public StructuralElementBasement getStructuralElementBasement() {
        return structuralElementBasement;
    }

    public void setStructuralElementBasement(StructuralElementBasement structuralElementBasement) {
        this.structuralElementBasement = structuralElementBasement;
    }

    public StructuralElementFrame getStructuralElementFrame() {
        return structuralElementFrame;
    }

    public void setStructuralElementFrame(StructuralElementFrame structuralElementFrame) {
        this.structuralElementFrame = structuralElementFrame;
    }

    public Calculation getCalculation() {
        return calculation;
    }

    public void setCalculation(Calculation calculation) {
        this.calculation = calculation;
    }

    public MaterialCharacteristic getMaterialCharacteristics() {
        return materialCharacteristics;
    }

    public void setMaterialCharacteristics(MaterialCharacteristic materialCharacteristics) {
        this.materialCharacteristics = materialCharacteristics;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Double getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(Double fullPrice) {
        this.fullPrice = fullPrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}