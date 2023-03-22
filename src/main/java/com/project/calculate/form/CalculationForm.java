package com.project.calculate.form;

public class CalculationForm {
    private Long id;
    private String date;
    private String status;
    private String adress;
    private int number;


    public CalculationForm(Long id, String date, String status, String adress, int number) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.adress = adress;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
