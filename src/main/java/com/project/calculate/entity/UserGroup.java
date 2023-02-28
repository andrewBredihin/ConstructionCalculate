package com.project.calculate.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "user_group")
public class UserGroup implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = Integer.MAX_VALUE)
    private String title;

    @ManyToMany
    @JoinTable(name = "users_group",
            joinColumns = @JoinColumn(name = "usersgroup_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id"))
    private Set<User> userTables = new LinkedHashSet<>();

    public UserGroup() {

    }

    public Set<User> getUserTables() {
        return userTables;
    }

    public void setUserTables(Set<User> userTables) {
        this.userTables = userTables;
    }

    public UserGroup(String title) {
        this.title = title;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    @Override
    public String getAuthority() {
        return getTitle();
    }
}