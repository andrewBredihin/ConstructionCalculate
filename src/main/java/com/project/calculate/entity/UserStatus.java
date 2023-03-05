package com.project.calculate.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "User_status")
public class UserStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "state")
    private Set<User> users = new LinkedHashSet<>();


    public UserStatus(Long id) {
        this.id = id;
    }

    public UserStatus() {

    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}