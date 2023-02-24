package com.project.calculate.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Price_list")
public class PriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_characteristics_id")
    private MaterialCharacteristic materialCharacteristics;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "purchase_price", nullable = false)
    private Double purchasePrice;

    @Column(name = "selling_price", nullable = false)
    private Double sellingPrice;

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public MaterialCharacteristic getMaterialCharacteristics() {
        return materialCharacteristics;
    }

    public void setMaterialCharacteristics(MaterialCharacteristic materialCharacteristics) {
        this.materialCharacteristics = materialCharacteristics;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}