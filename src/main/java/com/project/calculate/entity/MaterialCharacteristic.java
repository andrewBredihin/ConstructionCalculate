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


    @Column(name = "length", nullable = false)
    private Double length;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "thickness", nullable = false)
    private Double thickness;

    @Column(name = "volume", nullable = false)
    private Double volume;

    @Column(name = "wedth", nullable = false)
    private Double wedth;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "materials_id", nullable = false)
    private Material materials;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "measurement_unit_id", nullable = false)
    private MeasurementUnit measurementUnit;

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

    public MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(MeasurementUnit measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public Material getMaterials() {
        return materials;
    }

    public void setMaterials(Material materials) {
        this.materials = materials;
    }

    public Double getWedth() {
        return wedth;
    }

    public void setWedth(Double wedth) {
        this.wedth = wedth;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }
}