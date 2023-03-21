package com.project.calculate.form;

public class OpeningsForm {
    private int amount;
    private double width;
    private double height;

    public OpeningsForm() {}

    public OpeningsForm(int amount, double width, double height) {
        this.amount = amount;
        this.width = width;
        this.height = height;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
