package com.project.calculate.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Structural_element_basement")
public class StructuralElementBasement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "results_id")
    private Result results;

    @Column(name = "\"Perimeter_of_external_walls\"", nullable = false)
    private Double perimeterOfExternalWalls;

    @Column(name = "\"Internal_wall_length\"", nullable = false)
    private Double internalWallLength;

    @Column(name = "\"Concrete_piles\"", nullable = false)
    private String concretePiles;

    @Column(name = "\"Concrete\"", nullable = false)
    private String concrete;

    public String getConcrete() {
        return concrete;
    }

    public void setConcrete(String concrete) {
        this.concrete = concrete;
    }

    public String getConcretePiles() {
        return concretePiles;
    }

    public void setConcretePiles(String concretePiles) {
        this.concretePiles = concretePiles;
    }

    public Double getInternalWallLength() {
        return internalWallLength;
    }

    public void setInternalWallLength(Double internalWallLength) {
        this.internalWallLength = internalWallLength;
    }

    public Double getPerimeterOfExternalWalls() {
        return perimeterOfExternalWalls;
    }

    public void setPerimeterOfExternalWalls(Double perimeterOfExternalWalls) {
        this.perimeterOfExternalWalls = perimeterOfExternalWalls;
    }

    public Result getResults() {
        return results;
    }

    public void setResults(Result results) {
        this.results = results;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}