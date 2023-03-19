package com.project.calculate.form;

public class CalculationForCustomerInfo {
    private String date;
    private String number;
    private String adress;
    private String state;

    public CalculationForCustomerInfo(String date, String number, String adress, String state) {
        this.date = date;
        this.number = number;
        this.adress = adress;
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
