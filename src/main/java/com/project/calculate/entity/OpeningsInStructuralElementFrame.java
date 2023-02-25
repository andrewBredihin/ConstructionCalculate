package com.project.calculate.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Openings_InStructural_Element_Frame")
public class OpeningsInStructuralElementFrame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "structural_element_frame_id")
    private StructuralElementFrame structuralElementFrame;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "openings_id", nullable = false)
    private Opening openings;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Opening getOpenings() {
        return openings;
    }

    public void setOpenings(Opening openings) {
        this.openings = openings;
    }

    public StructuralElementFrame getStructuralElementFrame() {
        return structuralElementFrame;
    }

    public void setStructuralElementFrame(StructuralElementFrame structuralElementFrame) {
        this.structuralElementFrame = structuralElementFrame;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}