package com.project.calculate.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Calculation")
public class Calculation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "address_object_constractions", nullable = false)
    private String addressObjectConstractions;

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "results_id", nullable = false)
    private Result results;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"сalculation_state_id\"", nullable = false)
    private CalculationStatus сalculationState;

    public CalculationStatus getСalculationState() {
        return сalculationState;
    }

    public void setСalculationState(CalculationStatus сalculationState) {
        this.сalculationState = сalculationState;
    }

    public Result getResults() {
        return results;
    }

    public void setResults(Result results) {
        this.results = results;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getAddressObjectConstractions() {
        return addressObjectConstractions;
    }

    public void setAddressObjectConstractions(String addressObjectConstractions) {
        this.addressObjectConstractions = addressObjectConstractions;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}