package com.project.calculate.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Entity объект таблицы Calculation.
 * addressObjectConstractions - адрес,
 * createdDate - дата создания расчета,
 * number - номер расчета,
 * customer - пользователь, которому принадлежит данный расчет,
 * сalculationState - статус расчета,
 * results - результаты данного расчета.
 */
@Entity
@Table(name = "Calculation")
public class Calculation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "address_object_constractions", nullable = false)
    private String addressObjectConstractions;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "number", nullable = false)
    private Integer number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"сalculation_state_id\"", nullable = false)
    private CalculationStatus сalculationState;

    @OneToMany(mappedBy = "calculation", cascade = {CascadeType.ALL})
    private Set<Result> results = new LinkedHashSet<>();

    @Override
    public String toString() {
        return "Calculation{" +
                ", addressObjectConstractions='" + addressObjectConstractions + '\'' +
                ", createdDate=" + createdDate +
                ", number=" + number +
                ", customer=" + customer.getFullName() +
                ", сalculationState=" + сalculationState +
                '}';
    }

    public Set<Result> getResults() {
        return results;
    }

    public void setResults(Set<Result> results) {
        this.results = results;
    }

    public CalculationStatus getСalculationState() {
        return сalculationState;
    }

    public void setСalculationState(CalculationStatus сalculationState) {
        this.сalculationState = сalculationState;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getAddressObjectConstractions() {
        return addressObjectConstractions;
    }

    public void setAddressObjectConstractions(String addressObjectConstractions) {
        this.addressObjectConstractions = addressObjectConstractions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}