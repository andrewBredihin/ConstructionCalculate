package com.project.calculate.entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "Structural_element_basement")
public class StructuralElementBasement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "concrete", nullable = false)
    private String concrete;

    @Column(name = "concrete_piles", nullable = false)
    private String concretePiles;

    @Column(name = "internal_wall_length", nullable = false)
    private Double internalWallLength;

    @Column(name = "perimeter_of_external_walls", nullable = false)
    private Double perimeterOfExternalWalls;

    @OneToMany(mappedBy = "structuralElementBasement")
    private Set<Result> results = new LinkedHashSet<>();

    public Set<Result> getResults() {
        return results;
    }

    public void setResults(Set<Result> results) {
        this.results = results;
    }

    public Double getPerimeterOfExternalWalls() {
        return perimeterOfExternalWalls;
    }

    public void setPerimeterOfExternalWalls(Double perimeterOfExternalWalls) {
        this.perimeterOfExternalWalls = perimeterOfExternalWalls;
    }

    public Double getInternalWallLength() {
        return internalWallLength;
    }

    public void setInternalWallLength(Double internalWallLength) {
        this.internalWallLength = internalWallLength;
    }

    public String getConcretePiles() {
        return concretePiles;
    }

    public void setConcretePiles(String concretePiles) {
        this.concretePiles = concretePiles;
    }

    public String getConcrete() {
        return concrete;
    }

    public void setConcrete(String concrete) {
        this.concrete = concrete;
    }
}