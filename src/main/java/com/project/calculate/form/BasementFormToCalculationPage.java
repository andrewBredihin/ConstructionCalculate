package com.project.calculate.form;

import com.project.calculate.entity.Result;
import com.project.calculate.entity.StructuralElementBasement;

import java.util.Set;

public class BasementFormToCalculationPage {

    private Set<Result> results;
    private String fullPriceMoneyFormat;
    private double fullPrice;

    public BasementFormToCalculationPage(){}

    public BasementFormToCalculationPage(StructuralElementBasement basement){
        this.results = basement.getResults();
        double price = 0;
        for (Result x : results) {
            price += x.getPrice();
        }
        this.fullPrice = price;
        this.fullPriceMoneyFormat = String.format("%.2f", price) + " Руб";
    }

    public Set<Result> getResults() {
        return results;
    }

    public void setResults(Set<Result> results) {
        this.results = results;
    }

    public String getFullPriceMoneyFormat() {
        return fullPriceMoneyFormat;
    }

    public void setFullPriceMoneyFormat(String fullPriceMoneyFormat) {
        this.fullPriceMoneyFormat = fullPriceMoneyFormat;
    }

    public double getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(double fullPrice) {
        this.fullPrice = fullPrice;
    }
}
