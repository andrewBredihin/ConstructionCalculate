package com.project.calculate.entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Entity объект таблицы Calculation_status.
 * title - название статуса.
 */
@Entity
@Table(name = "Calculation_status")
public class CalculationStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "сalculationState")
    private Set<Calculation> calculations = new LinkedHashSet<>();

    public CalculationStatus(){}
    public CalculationStatus(Long id, String title){
        this.id = id;
        this.title = title;
    }

    public Set<Calculation> getCalculations() {
        return calculations;
    }

    public void setCalculations(Set<Calculation> calculations) {
        this.calculations = calculations;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}