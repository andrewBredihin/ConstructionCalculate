package com.project.calculate.entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "Material_characteristics")
public class MaterialCharacteristic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "measurement_unit_id", nullable = false)
    private MeasurementUnit measurementUnit;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "materials_id", nullable = false)
    private Material materials;

    @Column(name = "length", nullable = false)
    private Double length;

    @Column(name = "wedth", nullable = false)
    private Double wedth;

    @Column(name = "thickness", nullable = false)
    private Double thickness;

    @Column(name = "volume", nullable = false)
    private Double volume;

    @OneToMany(mappedBy = "materialCharacteristics")
    private Set<Result> results = new LinkedHashSet<>();

    @OneToMany(mappedBy = "materialCharacteristics")
    private Set<PriceList> priceLists = new LinkedHashSet<>();

    public Set<PriceList> getPriceLists() {
        return priceLists;
    }

    public void setPriceLists(Set<PriceList> priceLists) {
        this.priceLists = priceLists;
    }

    public Set<Result> getResults() {
        return results;
    }

    public void setResults(Set<Result> results) {
        this.results = results;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getThickness() {
        return thickness;
    }

    public void setThickness(Double thickness) {
        this.thickness = thickness;
    }

    public Double getWedth() {
        return wedth;
    }

    public void setWedth(Double wedth) {
        this.wedth = wedth;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Material getMaterials() {
        return materials;
    }

    public void setMaterials(Material materials) {
        this.materials = materials;
    }

    public MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(MeasurementUnit measurementUnit) {
        this.measurementUnit = measurementUnit;
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