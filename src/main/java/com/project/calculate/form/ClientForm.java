package com.project.calculate.form;

import com.project.calculate.entity.Customer;
import com.project.calculate.entity.User;

public class ClientForm {
    private String adress;
    private String email;
    private String first_name;
    private String last_name;
    private String second_name;
    private String phone;
    private Long id;
    private User manager;

    public ClientForm(){
    }

    public ClientForm(String adress, String email, String first_name, String last_name, String second_name, String phone) {
        this.adress = adress;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.second_name = second_name;
        this.phone = phone;
    }

    public ClientForm(Customer client){
        this.adress = client.getAdress();
        this.email = client.getEmail();
        this.first_name = client.getFirstName();
        this.last_name = client.getLastName();
        this.second_name = client.getSecondName();
        this.phone = client.getPhone().toString();
        this.id = client.getId();
        this.manager = client.getManager();
    }

    @Override
    public String toString() {
        return "ClientForm{" +
                "adress='" + adress + '\'' +
                ", email='" + email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", second_name='" + second_name + '\'' +
                ", phone='" + phone + '\'' +
                ", id=" + id +
                ", manager=" + manager +
                '}';
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_nae) {
        this.first_name = first_nae;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }
}
