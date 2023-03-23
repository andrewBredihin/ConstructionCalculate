package com.project.calculate.entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "Openings")
public class Opening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "width", nullable = false)
    private Double width;

    @Column(name = "height", nullable = false)
    private Double height;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "openings_in_structural_element_frame",
            joinColumns = @JoinColumn(name = "opening_id"),
            inverseJoinColumns = @JoinColumn(name = "frame_id"))
    private Set<StructuralElementFrame> structuralElementFrames = new LinkedHashSet<>();

    @Override
    public String toString() {
        return "Opening{" +
                ", type='" + type + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", amount=" + amount +
                ", structuralElementFrames=" + structuralElementFrames +
                '}';
    }

    public Set<StructuralElementFrame> getStructuralElementFrames() {
        return structuralElementFrames;
    }

    public void setStructuralElementFrames(Set<StructuralElementFrame> structuralElementFrames) {
        this.structuralElementFrames = structuralElementFrames;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}