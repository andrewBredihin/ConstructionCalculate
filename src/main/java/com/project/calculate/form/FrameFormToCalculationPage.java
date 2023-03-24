package com.project.calculate.form;

import com.project.calculate.entity.Result;

import java.util.Set;

public class FrameFormToCalculationPage {
    private int floorNumber;
    private Set<Result> resultsExternal;
    private Set<Result> resultsInternal;
    private Set<Result> resultsOverlap;
    private String externalPrice;
    private String internalPrice;
    private String overlapPrice;
    private String fullPriceMoneyFormat;
    private double fullPrice;

    public FrameFormToCalculationPage(){}

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Set<Result> getResultsExternal() {
        return resultsExternal;
    }

    public void setResultsExternal(Set<Result> resultsExternal) {
        this.resultsExternal = resultsExternal;
    }

    public Set<Result> getResultsInternal() {
        return resultsInternal;
    }

    public void setResultsInternal(Set<Result> resultsInternal) {
        this.resultsInternal = resultsInternal;
    }

    public Set<Result> getResultsOverlap() {
        return resultsOverlap;
    }

    public void setResultsOverlap(Set<Result> resultsOverlap) {
        this.resultsOverlap = resultsOverlap;
    }

    public String getExternalPrice() {
        return externalPrice;
    }

    public void setExternalPrice(String externalPrice) {
        this.externalPrice = externalPrice;
    }

    public String getInternalPrice() {
        return internalPrice;
    }

    public void setInternalPrice(String internalPrice) {
        this.internalPrice = internalPrice;
    }

    public String getOverlapPrice() {
        return overlapPrice;
    }

    public void setOverlapPrice(String overlapPrice) {
        this.overlapPrice = overlapPrice;
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
