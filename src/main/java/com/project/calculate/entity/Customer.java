package com.project.calculate.entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "Customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "second_name", nullable = false)
    private String secondName;

    @Column(name = "phone", nullable = false)
    private Long phone;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "adress", nullable = false)
    private String adress;

    @OneToMany(mappedBy = "customer")
    private Set<Calculation> calculations = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User manager;

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public Set<Calculation> getCalculations() {
        return calculations;
    }

    public void setCalculations(Set<Calculation> calculations) {
        this.calculations = calculations;
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

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstLastName(){
        return firstName + " " + lastName;
    }

    public String getFullName(){
        return lastName + " " + firstName + " " + secondName;
    }

    public String getPhoneMask(){
        String phoneToString = Long.toString(phone);
        String phone = "";
        for (int i = 0; i < 11; i++){
            if (i == 1)
                phone += " (" + phoneToString.charAt(i);
            else if (i == 4)
                phone += ") " + phoneToString.charAt(i);
            else if (i == 7 || i == 9)
                phone += "-" + phoneToString.charAt(i);
            else
                phone += phoneToString.charAt(i);
        }
        return phone;
    }
}