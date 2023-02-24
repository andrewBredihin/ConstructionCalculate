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
    private Integer id;

    @Lob
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Lob
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Lob
    @Column(name = "second_name", nullable = false)
    private String secondName;

    @Column(name = "phone", nullable = false)
    private Integer phone;

    @Lob
    @Column(name = "email", nullable = false)
    private String email;

    @Lob
    @Column(name = "adress", nullable = false)
    private String adress;

    @OneToMany(mappedBy = "customer")
    private Set<User> users = new LinkedHashSet<>();

    @OneToMany(mappedBy = "customer")
    private Set<Calculation> calculations = new LinkedHashSet<>();

    public Set<Calculation> getCalculations() {
        return calculations;
    }

    public void setCalculations(Set<Calculation> calculations) {
        this.calculations = calculations;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
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

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}