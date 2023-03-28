package com.project.calculate.form;

public class FoundationForm {

    private double externalWallLength;
    private double internalWallLength;
    private String concrete;
    private String concretePiles;

    public FoundationForm(){}

    public double getExternalWallLength() {
        return externalWallLength;
    }

    public void setExternalWallLength(double externalWallLength) {
        this.externalWallLength = externalWallLength;
    }

    public double getInternalWallLength() {
        return internalWallLength;
    }

    public void setInternalWallLength(double internalWallLength) {
        this.internalWallLength = internalWallLength;
    }

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
}
