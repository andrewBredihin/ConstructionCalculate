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
    private Integer id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "width", nullable = false)
    private Double width;

    @Column(name = "height", nullable = false)
    private Double height;

    @OneToMany(mappedBy = "openings")
    private Set<OpeningsInStructuralElementFrame> openingsInStructuralElementFrames = new LinkedHashSet<>();

    public Set<OpeningsInStructuralElementFrame> getOpeningsInStructuralElementFrames() {
        return openingsInStructuralElementFrames;
    }

    public void setOpeningsInStructuralElementFrames(Set<OpeningsInStructuralElementFrame> openingsInStructuralElementFrames) {
        this.openingsInStructuralElementFrames = openingsInStructuralElementFrames;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}