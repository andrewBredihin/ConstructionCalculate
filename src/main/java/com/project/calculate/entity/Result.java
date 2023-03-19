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

    @ManyToMany
    @JoinTable(name = "frame_results",
            joinColumns = @JoinColumn(name = "result_id"),
            inverseJoinColumns = @JoinColumn(name = "frame_id"))
    private Set<StructuralElementFrame> structuralElementFrames = new LinkedHashSet<>();

    public Set<StructuralElementFrame> getStructuralElementFrames() {
        return structuralElementFrames;
    }

    public void setStructuralElementFrames(Set<StructuralElementFrame> structuralElementFrames) {
        this.structuralElementFrames = structuralElementFrames;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}